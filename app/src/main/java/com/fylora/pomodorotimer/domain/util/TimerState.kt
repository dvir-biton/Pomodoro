package com.fylora.pomodorotimer.domain.util

sealed interface TimerState {
    data object Pomodoro: TimerState
    data object ShortBreak: TimerState {
        override fun toString(): String = "Short Break"
    }
    data object LongBreak: TimerState {
        override fun toString(): String = "Long Break"
    }
}