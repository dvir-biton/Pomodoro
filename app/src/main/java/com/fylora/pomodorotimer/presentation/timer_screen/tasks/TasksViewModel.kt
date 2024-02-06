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
import com.fylora.pomodorotimer.presentation.timer_screen.event.EventManager
import com.fylora.pomodorotimer.presentation.timer_screen.event.PomodoroEvent
import com.fylora.pomodorotimer.presentation.util.DropDownItem
import com.fylora.pomodorotimer.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
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

    private var editedTask: Task? = null

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
        viewModelScope.launch {
            EventManager.pomodoroEvent.collect { event ->
                when (event) {
                    PomodoroEvent.SessionEnded -> {
                        _tasks.value.find { it.isSelected }?.let { currentTask ->
                            val updatedSessions = currentTask.currentSessions + 1.0
                            val updatedTask = currentTask.copy(currentSessions = updatedSessions)

                            repository.upsertTask(
                                updatedTask
                            )
                        }
                    }
                }
            }
        }
    }

    fun upsertTask(): Boolean {
        var isSuccessful = true
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
                repository.upsertTask(task.copy(id = editedTask?.id))
                editedTask = null
                _state.value = TasksState()
            } catch (e: InvalidTaskException) {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        e.message ?: "Could not create task"
                    )
                )
                updateStateError()
                isSuccessful = false
            } catch (e: NumberFormatException) {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        "No field can be empty"
                    )
                )
                updateStateError()
                isSuccessful = false
            }
        }
        return isSuccessful
    }

    fun onEvent(event: TasksEvent) {
        when(event) {
            is TasksEvent.UpsertTask -> {
                upsertTask()
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
                    hoursTextFieldValue = event.value,
                    isHoursError = false
                )
            }
            is TasksEvent.MinutesTextFieldValueChanged -> {
                val isValid = isValidTimeFormat(event.value, "minutes")
                if(!isValid)
                    return

                _state.value = state.value.copy(
                    minutesTextFieldValue = event.value,
                    isMinutesError = false
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
                    titleTextFieldValue = event.value,
                    isTitleError = false
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
                    if(event.task.isSelected) {
                        repository.upsertTask(
                            event.task.copy(
                                isSelected = false
                            )
                        )

                        return@launch
                    }
                    _tasks.value.find { it.isSelected }?.let { task ->
                        repository.upsertTask(
                            task.copy(
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
            }
            is TasksEvent.OnDropDownSelect -> {
                when(event.item) {
                    DropDownItem.Delete -> {
                        viewModelScope.launch {
                            repository.deleteTask(event.task)
                        }
                    }
                    DropDownItem.Edit -> {
                        _state.value = TasksState()
                        _state.value = state.value.copy(
                            titleTextFieldValue = event.task.title,
                            hoursTextFieldValue = event.task.workTime.hour.toString(),
                            minutesTextFieldValue = event.task.workTime.minute.toString(),
                            dueDateValue = event.task.dueDate,
                            isAddTaskDialogOpen = true
                        )
                        editedTask = event.task
                    }
                }
            }
        }
    }

    private fun isValidTimeFormat(time: String, type: String): Boolean {
        if(!time.isDigitsOnly())
            return false
        if(time.isBlank())
            return true

        val timeValue = time.toInt()
        val maxValue = when(type) {
            "minutes" -> 60
            "hours" -> 24
            else -> return false
        }

        return timeValue <= maxValue
    }

    private fun updateStateError() {
        when {
            state.value.titleTextFieldValue.isBlank() ->
                _state.value = state.value.copy(isTitleError = true)
            state.value.hoursTextFieldValue.isBlank() ->
                _state.value = state.value.copy(isHoursError = true)
            state.value.minutesTextFieldValue.isBlank() ->
                _state.value = state.value.copy(isMinutesError = true)
        }
    }
}