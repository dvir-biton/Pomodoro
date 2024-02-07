package com.fylora.pomodorotimer.presentation.timer_screen.timer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.pomodorotimer.core.Globals
import com.fylora.pomodorotimer.domain.notifications.NotificationService
import com.fylora.pomodorotimer.domain.util.TimerState
import com.fylora.pomodorotimer.presentation.timer_screen.event.EventManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class TimerScreenViewModel @Inject constructor(
    private val notificationService: NotificationService
): ViewModel() {

    private val _state = mutableStateOf(TimerScreenState())
    val state = _state

    private var timer: Timer? = null

    fun onEvent(event: TimerEvent) {
        when(event) {
            is TimerEvent.ChangeTimerState -> {
                _state.value = state.value.copy(timerState = event.newState)
                resetTimer()
            }
            TimerEvent.StartStopTimer ->
                toggleTimer()
        }
    }

    private fun toggleTimer() {
        if(_state.value.isTimerRunning || timer != null) {
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
            }, 1000, 1000)
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
            viewModelScope.launch {
                EventManager.onEndPomodoroSession()
            }
            notificationService.showNotification(
                title = "Pomodoro Ended!",
                message = "It's time to take a break :)"
            )
        } else {
            _state.value = state.value.copy(
                timerState = TimerState.Pomodoro,
                longBreakIndicator = if(state.value.timerState is TimerState.LongBreak) 0
                else state.value.longBreakIndicator
            )
            notificationService.showNotification(
                title = "Break Ended!",
                message = "Back to work :)"
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
        timer = null
        _state.value = state.value.copy(
            isTimerRunning = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}