package com.fylora.pomodorotimer.presentation.timer_screen.components.header.timer_state_section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.fontFamily
import com.fylora.pomodorotimer.domain.util.TimerState

@Composable
fun TimerSectionItem(
    state: TimerState,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.background
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = 5.5.dp,
                horizontal = 10.dp
            ),
            text = state.toString(),
            fontSize = 16.sp,
            fontFamily = fontFamily,
            fontWeight = if(isSelected) FontWeight.Medium else FontWeight.Light,
            color = if(isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
            letterSpacing = 0.8.sp,
        )
    }
}

@Preview
@Composable
fun TimerSectionItemPreview() {
    TimerSectionItem(state = TimerState.Pomodoro, isSelected = false) {}
}

@Preview
@Composable
fun TimerSectionItemPreviewSelected() {
    TimerSectionItem(state = TimerState.Pomodoro, isSelected = true) {}
}