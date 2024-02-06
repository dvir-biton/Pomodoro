package com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.fontFamily

@Composable
fun DialogTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    bottomLabel: String = "",
    isError: Boolean = false,
    icon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val backgroundColor = if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.secondary

    Column {
        if (label.isNotBlank()) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = fontFamily,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.7.sp,
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.7.sp,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        if (bottomLabel.isNotBlank()) {
            Text(
                text = bottomLabel,
                fontSize = 8.sp,
                fontFamily = fontFamily,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.7.sp,
            )
        }
    }
}

@Preview
@Composable
fun PreviewDialogTextField() {
    DialogTextField(value = "this is the text", onValueChange = {}, label = "Test", icon = Icons.Default.DateRange)
}