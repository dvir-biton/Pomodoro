package com.fylora.pomodorotimer.presentation.timer_screen.event

sealed interface PomodoroEvent {
    data object SessionEnded: PomodoroEvent
}