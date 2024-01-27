package com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fylora.pomodorotimer.core.Globals
import com.fylora.pomodorotimer.domain.util.TimerState
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header.timer_state_section.TimerSectionBar

@Composable
fun Header(
    isTimerRunning: Boolean = false,
    onStartStop: () -> Unit,
    onStateChange: (state: TimerState) -> Unit,
    state: TimerState = TimerState.Pomodoro,
    minutes: Int = Globals.sessionLength,
    seconds: Int = 0,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 35.dp,
                horizontal = 15.dp
            ),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TimerSectionBar(
            selected = state,
            onSelect = onStateChange
        )

        CountdownTimer(
            minutes = minutes,
            seconds = seconds
        )

        StartButton(
            isStarted = isTimerRunning,
            onClick = onStartStop
        )
    }
}