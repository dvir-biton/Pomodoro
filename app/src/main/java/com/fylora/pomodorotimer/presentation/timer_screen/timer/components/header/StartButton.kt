package com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.fontFamily

@Composable
fun StartButton(
    isStarted: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(
                MaterialTheme.colorScheme.primary
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 14.dp),
            text = if(isStarted) "Pause" else "Start",
            fontSize = 24.sp,
            fontFamily = fontFamily,
            color = MaterialTheme.colorScheme.background,
            letterSpacing = 1.2.sp
        )
    }
}

@Preview
@Composable
fun ActionButtonPreviewPreviewPrimary() {
    StartButton(isStarted = false) {}
}

@Preview
@Composable
fun ActionButtonPreviewPreviewSecondary() {
    StartButton(isStarted = true) {}
}