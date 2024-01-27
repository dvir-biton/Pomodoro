package com.fylora.pomodorotimer.presentation.timer_screen.timer

import com.fylora.pomodorotimer.domain.util.TimerState

sealed interface TimerEvent {
    data object StartStopTimer: TimerEvent
    data class ChangeTimerState(val newState: TimerState): TimerEvent
}