package com.fylora.pomodorotimer.presentation.timer_screen.tasks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.pomodorotimer.domain.model.InvalidTaskException
import com.fylora.pomodorotimer.domain.model.Task
import com.fylora.pomodorotimer.domain.repository.TaskRepository
import com.fylora.pomodorotimer.domain.use_case.CalculateSessions
import com.fylora.pomodorotimer.presentation.util.DropDownItem
import com.fylora.pomodorotimer.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val calculateSessions: CalculateSessions
): ViewModel() {

    private val _tasks: MutableState<List<Task>> = mutableStateOf(emptyList())
    val tasks = _tasks

    private val _state = mutableStateOf(TasksState())
    val state = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun onEvent(event: TasksEvent) {
        when(event) {
            is TasksEvent.UpsertTask -> {
                viewModelScope.launch {
                    try {
                        val workTime = LocalTime.of(
                            state.value.hoursTextFieldValue.toInt(),
                            state.value.minutesTextFieldValue.toInt()
                        )
                        val task = Task(
                            title = state.value.titleTextFieldValue,
                            workTime = workTime,
                            dueDate = state.value.dueDateValue,
                            totalSessions = calculateSessions(workTime),
                        )
                        repository.upsertTask(task)
                    } catch (e: InvalidTaskException) {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                e.message ?: "Could not create task"
                            )
                        )
                    }
                }
            }
            is TasksEvent.DueDateValueChanged -> {
                _state.value = state.value.copy(
                    dueDateValue = event.newDate
                )
            }
            is TasksEvent.HoursTextFieldValueChanged -> {
                val isValid = isValidTimeFormat(event.value, "hours")
                if(!isValid)
                    return

                _state.value = state.value.copy(
                    hoursTextFieldValue = event.value
                )
            }
            is TasksEvent.MinutesTextFieldValueChanged -> {
                val isValid = isValidTimeFormat(event.value, "minutes")
                if(!isValid)
                    return

                _state.value = state.value.copy(
                    minutesTextFieldValue = event.value
                )
            }
            TasksEvent.OpenCloseAddTaskDialog -> {
                _state.value = state.value.copy(
                    isAddTaskDialogOpen = !state.value.isAddTaskDialogOpen
                )
            }
            TasksEvent.OpenCloseDatePicker -> {
                _state.value = state.value.copy(
                    isDatePickerOpen = !state.value.isDatePickerOpen
                )
            }
            is TasksEvent.TitleTextFieldValueChanged -> {
                _state.value = state.value.copy(
                    titleTextFieldValue = event.value
                )
            }
            is TasksEvent.ToggleCheckTask -> {
                val newTask = event.task.copy(isCompleted = !event.task.isCompleted)
                viewModelScope.launch {
                    repository.upsertTask(newTask)
                }
            }
            is TasksEvent.OnTaskSelect -> {
                viewModelScope.launch {
                    state.value.selectedTask?.let { oldSelectedTask ->
                        repository.upsertTask(
                            oldSelectedTask.copy(
                                isSelected = false
                            )
                        )
                    }
                    repository.upsertTask(
                        event.task.copy(
                            isSelected = true
                        )
                    )
                }
                _state.value = state.value.copy(
                    selectedTask = event.task
                )
            }
            is TasksEvent.OnDropDownSelect -> {
                when(event.item) {
                    DropDownItem.Delete -> {
                        viewModelScope.launch {
                            repository.deleteTask(event.task)
                        }
                    }
                    DropDownItem.Expand -> TODO()
                }
            }
        }
    }

    private fun isValidTimeFormat(time: String, type: String): Boolean {
        if(!time.isDigitsOnly())
            return false

        val timeValue = time.toInt()
        val maxValue = when(type) {
            "minutes" -> 60
            "hours" -> 24
            else -> return false
        }

        return timeValue >= maxValue
    }
}