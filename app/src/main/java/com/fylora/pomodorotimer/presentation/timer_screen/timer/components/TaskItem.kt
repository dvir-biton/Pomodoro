package com.fylora.pomodorotimer.presentation.timer_screen.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fylora.pomodorotimer.core.fontFamily
import com.fylora.pomodorotimer.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskItem(
    task: Task,
    onCheckChange: () -> Unit,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 20.dp,
                horizontal = 15.dp
            )
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = if(task.isSelected)
                    MaterialTheme.colorScheme.primary
                else Color.Transparent,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.title,
                    fontSize = 24.sp,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.8.sp,
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                )

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        onCheckChange()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier.scale(1.3f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Due Date",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = " Due to " + task.dueDate.format(DateTimeFormatter.ofPattern("dd MMM")),
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.6.sp,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = String.format("%.0f", task.currentSessions),
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.6.sp,
                    )
                    Text(
                        text = "/",
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.6.sp,
                    )
                    Text(
                        text = String.format("%.0f", task.totalSessions),
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.6.sp,
                    )
                    Text(
                        text = " sessions",
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.6.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(
            title = "this is the task",
            workTime = LocalTime.now(),
            dueDate = LocalDate.now().plusDays(1),
            totalSessions = 4.0,
        ),
        onCheckChange = {},
        onSelect = {},
    )
}