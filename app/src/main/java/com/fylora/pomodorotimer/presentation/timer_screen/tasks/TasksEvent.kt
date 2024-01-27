package com.fylora.pomodorotimer.presentation.timer_screen.tasks

import com.fylora.pomodorotimer.domain.model.Task
import com.fylora.pomodorotimer.presentation.util.DropDownItem
import java.time.LocalDate

sealed interface TasksEvent {
    data class TitleTextFieldValueChanged(val value: String): TasksEvent
    data class HoursTextFieldValueChanged(val value: String): TasksEvent
    data class MinutesTextFieldValueChanged(val value: String): TasksEvent
    data class DueDateValueChanged(val newDate: LocalDate): TasksEvent
    data class ToggleCheckTask(val task: Task): TasksEvent
    data class OnTaskSelect(val task: Task): TasksEvent
    data class OnDropDownSelect(val task: Task, val item: DropDownItem): TasksEvent
    data object UpsertTask: TasksEvent
    data object OpenCloseDatePicker: TasksEvent
    data object OpenCloseAddTaskDialog: TasksEvent
}