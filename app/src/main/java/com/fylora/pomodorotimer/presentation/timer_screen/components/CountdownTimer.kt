package com.fylora.pomodorotimer.presentation.timer_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.fontFamily

@Composable
fun CountdownTimer(
    minutes: Int,
    seconds: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = minutes.toString(),
            fontSize = 96.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 4.8.sp,
        )
        Text(
            text = ":",
            fontSize = 96.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 4.8.sp,
        )
        Text(
            text = seconds.toString(),
            fontSize = 96.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 4.8.sp,
        )
    }
}

@Preview
@Composable
fun PreviewCountdownTimer() {
    CountdownTimer(minutes = 12, seconds = 32)
}