package com.fylora.pomodorotimer.presentation.timer_screen

import com.fylora.pomodorotimer.domain.model.Task
import com.fylora.pomodorotimer.domain.util.TimerState

sealed interface TimerEvent {
    data object StartStopTimer: TimerEvent
    data object TriggerNewTaskDialog: TimerEvent
    data class DeleteTask(val task: Task): TimerEvent
    data class UpsertTask(val task: Task): TimerEvent
    data class ChangeTimerState(val newState: TimerState): TimerEvent
}