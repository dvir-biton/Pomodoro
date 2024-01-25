package com.fylora.pomodorotimer.presentation.timer_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.pomodorotimer.domain.model.InvalidTaskException
import com.fylora.pomodorotimer.domain.repository.TaskRepository
import com.fylora.pomodorotimer.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerScreenViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    private val _state = mutableStateOf(TimerScreenState())
    val state = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _state.value = state.value.copy(tasks = it)
            }
        }
    }

    fun onEvent(event: TimerEvent) {
        when(event) {
            is TimerEvent.ChangeTimerState ->
                _state.value = state.value.copy(timerState = event.newState)
            TimerEvent.StartStopTimer ->
                _state.value = state.value.copy(isTimerRunning = !state.value.isTimerRunning)
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
}