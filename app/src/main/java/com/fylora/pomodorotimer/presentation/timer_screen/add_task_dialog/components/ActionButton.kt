package com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.Fonts.fontFamily

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isPrimary: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if(isPrimary) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.background
    val borderColor = if(isPrimary) Color.Transparent
        else MaterialTheme.colorScheme.primary
    val textColor = if(isPrimary) MaterialTheme.colorScheme.background
        else MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            fontSize = 10.sp,
            color = textColor,
        )
    }
}

@Preview
@Composable
fun PreviewActionButtonPrimary() {
    ActionButton(text = "Save", isPrimary = true, modifier = Modifier.size(80.dp, 25.dp)) {

    }
}

@Preview
@Composable
fun PreviewActionButtonNotPrimary() {
    ActionButton(text = "Cancel", isPrimary = false, modifier = Modifier.size(80.dp, 25.dp)) {

    }
}