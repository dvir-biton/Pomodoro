package com.fylora.pomodorotimer.presentation.timer_screen.tasks

import com.fylora.pomodorotimer.domain.model.Task
import java.time.LocalDate

sealed interface TasksEvent {
    data class TitleTextFieldValueChanged(val value: String): TasksEvent
    data class HoursTextFieldValueChanged(val value: String): TasksEvent
    data class MinutesTextFieldValueChanged(val value: String): TasksEvent
    data class DueDateValueChanged(val newDate: LocalDate): TasksEvent
    data class DeleteTask(val task: Task): TasksEvent
    data object UpsertTask: TasksEvent
    data object OpenCloseDatePicker: TasksEvent
    data object OpenCloseAddTaskDialog: TasksEvent
}