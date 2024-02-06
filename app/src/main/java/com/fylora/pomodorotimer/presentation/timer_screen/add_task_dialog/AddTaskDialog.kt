package com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.pomodorotimer.core.fontFamily
import com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog.components.ActionButton
import com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog.components.DatePickerField
import com.fylora.pomodorotimer.presentation.timer_screen.add_task_dialog.components.DialogTextField
import com.fylora.pomodorotimer.presentation.timer_screen.tasks.TasksEvent
import com.fylora.pomodorotimer.presentation.timer_screen.tasks.TasksViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskDialog(
    tasksViewModel: TasksViewModel = hiltViewModel(),
) {
    if (!tasksViewModel.state.value.isAddTaskDialogOpen)
        return

    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = {
            tasksViewModel.onEvent(
                TasksEvent.OpenCloseAddTaskDialog
            )
            keyboardController?.hide()
        }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                DialogTextField(
                    value = tasksViewModel.state.value.titleTextFieldValue,
                    label =  "Title",
                    onValueChange = { value ->
                        tasksViewModel.onEvent(
                            TasksEvent.TitleTextFieldValueChanged(
                                value = value
                            )
                        )
                    },
                    isError = tasksViewModel.state.value.isTitleError
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DatePickerField(
                            modifier = Modifier
                                .size(120.dp, 40.dp),
                            value = tasksViewModel.state.value.dueDateValue,
                            onValueChanged = { date ->
                                tasksViewModel.onEvent(
                                    TasksEvent.DueDateValueChanged(
                                        newDate = date
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = "Estimated time",
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 0.7.sp,
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DialogTextField(
                                modifier = Modifier.size(45.dp, 40.dp),
                                value = tasksViewModel.state.value.hoursTextFieldValue,
                                onValueChange = { value ->
                                    tasksViewModel.onEvent(
                                        TasksEvent.HoursTextFieldValueChanged(
                                            value = value
                                        )
                                    )
                                },
                                bottomLabel = "Hours",
                                isError = tasksViewModel.state.value.isHoursError,
                                keyboardType = KeyboardType.Number
                            )
                            Text(
                                text = ":",
                                fontSize = 36.sp,
                                fontFamily = fontFamily,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            DialogTextField(
                                modifier = Modifier.size(45.dp, 40.dp),
                                value = tasksViewModel.state.value.minutesTextFieldValue,
                                onValueChange = { value ->
                                    tasksViewModel.onEvent(
                                        TasksEvent.MinutesTextFieldValueChanged(
                                            value = value
                                        )
                                    )
                                },
                                bottomLabel = "Minutes",
                                isError = tasksViewModel.state.value.isMinutesError,
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.size(77.dp, 25.dp),
                        text = "Cancel",
                        isPrimary = false,
                        onClick = {
                            tasksViewModel.onEvent(
                                TasksEvent.OpenCloseAddTaskDialog
                            )
                            keyboardController?.hide()
                        }
                    )
                    ActionButton(
                        modifier = Modifier.size(77.dp, 25.dp),
                        text = "Save",
                        isPrimary = true,
                        onClick = {
                            val isSuccessful = tasksViewModel.upsertTask()
                            if (isSuccessful) {
                                tasksViewModel.onEvent(
                                    TasksEvent.OpenCloseAddTaskDialog
                                )
                                keyboardController?.hide()
                            }
                        }
                    )
                }
            }
        }
    }
}