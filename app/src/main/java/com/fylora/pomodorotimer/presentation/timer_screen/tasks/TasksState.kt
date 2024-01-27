package com.fylora.pomodorotimer.presentation.timer_screen.tasks

import java.time.LocalDate

data class TasksState(
    val titleTextFieldValue: String = "",
    val hoursTextFieldValue: String = "",
    val minutesTextFieldValue: String = "",

    val isTitleError: Boolean = false,
    val isHoursError: Boolean = false,
    val isMinutesError: Boolean = false,
    val isDueDateError: Boolean = false,

    val dueDateValue: LocalDate = LocalDate.now(),

    val isDatePickerOpen: Boolean = false,
    val isAddTaskDialogOpen: Boolean = false
)