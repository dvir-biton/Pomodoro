package com.fylora.pomodorotimer.presentation.timer_screen.components.header.timer_state_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fylora.pomodorotimer.domain.util.TimerState

@Composable
fun TimerSectionBar(
    selected: TimerState,
    onSelect: (TimerState) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf(
            TimerState.Pomodoro,
            TimerState.ShortBreak,
            TimerState.LongBreak
        ).forEach { state ->
            TimerSectionItem(
                state = state,
                isSelected = selected == state,
                onClick = {
                    onSelect(state)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerSectionBarPreview() {
    TimerSectionBar(selected = TimerState.Pomodoro, onSelect = {})
}