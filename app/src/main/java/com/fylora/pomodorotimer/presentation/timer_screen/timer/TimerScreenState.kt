package com.fylora.pomodorotimer.presentation.timer_screen.timer

import com.fylora.pomodorotimer.core.Globals
import com.fylora.pomodorotimer.domain.util.TimerState

data class TimerScreenState(
    val timerState: TimerState = TimerState.Pomodoro,
    val longBreakIndicator: Int = 0,
    val isTimerRunning: Boolean = false,
    val minutes: Int = Globals.sessionLength,
    val seconds: Int = 0,
)