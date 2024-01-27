package com.fylora.pomodorotimer.presentation.timer_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.pomodorotimer.core.Globals
import com.fylora.pomodorotimer.domain.model.InvalidTaskException
import com.fylora.pomodorotimer.domain.repository.TaskRepository
import com.fylora.pomodorotimer.domain.util.TimerState
import com.fylora.pomodorotimer.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class TimerScreenViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    private val _state = mutableStateOf(TimerScreenState())
    val state = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var timer: Timer? = null

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _state.value = state.value.copy(tasks = it)
            }
        }
    }

    fun onEvent(event: TimerEvent) {
        when(event) {
            is TimerEvent.ChangeTimerState -> {
                _state.value = state.value.copy(timerState = event.newState)
                resetTimer()
            }
            TimerEvent.StartStopTimer ->
                toggleTimer()
            TimerEvent.TriggerNewTaskDialog ->
                state.value = state.value.copy(isAddTaskPopUpEnabled = !state.value.isAddTaskPopUpEnabled)
            is TimerEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                }
            }
            is TimerEvent.UpsertTask -> {
                viewModelScope.launch {
                    try {
                        repository.upsertTask(event.task)
                    } catch (e: InvalidTaskException) {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                e.message ?: "Could not create task"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun toggleTimer() {
        if(_state.value.isTimerRunning) {
            stopTimer()
            return
        }

        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val totalSeconds = _state.value.minutes * 60 + _state.value.seconds

                    if(totalSeconds <= 0) {
                        onTimerFinished()
                        return
                    }

                    val newSeconds = totalSeconds - 1
                    _state.value = state.value.copy(
                        minutes = newSeconds / 60,
                        seconds = newSeconds % 60
                    )
                }
            }, 0, 1000)
        }
        state.value = _state.value.copy(isTimerRunning = !_state.value.isTimerRunning)
    }

    private fun onTimerFinished() {
        stopTimer()

        if(state.value.timerState is TimerState.Pomodoro) {
            val newLongBreakIndicator = (state.value.longBreakIndicator + 1) % Globals.longBreakInterval
            _state.value = state.value.copy(
                longBreakIndicator = newLongBreakIndicator,
                timerState = if(newLongBreakIndicator == 0)
                    TimerState.LongBreak
                else TimerState.ShortBreak
            )
        } else {
            _state.value = state.value.copy(
                timerState = TimerState.Pomodoro,
                longBreakIndicator = if(state.value.timerState is TimerState.LongBreak) 0
                else state.value.longBreakIndicator
            )
        }

        resetTimer()
    }

    private fun resetTimer() {
        stopTimer()

        val newMinutes = when(state.value.timerState) {
            TimerState.LongBreak -> Globals.longBreakLength
            TimerState.Pomodoro -> Globals.sessionLength
            TimerState.ShortBreak -> Globals.shortBreakLength
        }
        _state.value = state.value.copy(
            minutes = newMinutes,
            seconds = 0
        )
    }

    private fun stopTimer() {
        timer?.cancel()
        _state.value = state.value.copy(
            isTimerRunning = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}