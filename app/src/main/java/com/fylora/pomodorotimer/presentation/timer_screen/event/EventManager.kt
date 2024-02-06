package com.fylora.pomodorotimer.presentation.timer_screen.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventManager {
    private val _pomodoroEvent = MutableSharedFlow<PomodoroEvent>()
    val pomodoroEvent = _pomodoroEvent.asSharedFlow()

    suspend fun onEndPomodoroSession() {
        _pomodoroEvent.emit(PomodoroEvent.SessionEnded)
    }
}