package com.fylora.pomodorotimer.domain.util

sealed interface TimerState {
    data object Pomodoro: TimerState
    data object ShortBreak: TimerState
    data object LongBreak: TimerState
}