package com.fylora.pomodorotimer.presentation.timer_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.tasks.TasksEvent
import com.fylora.pomodorotimer.presentation.timer_screen.tasks.TasksViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerEvent
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerScreenViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.TaskItem
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header.Header
import com.fylora.pomodorotimer.presentation.util.UiEvent

@Composable
fun TimerScreen(
    timerViewModel: TimerScreenViewModel = hiltViewModel(),
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        tasksViewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tasksViewModel.onEvent(
                        TasksEvent.OpenCloseAddTaskDialog
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Header(
                isTimerRunning = timerViewModel.state.value.isTimerRunning,
                onStartStop = {
                    timerViewModel.onEvent(TimerEvent.StartStopTimer)
                },
                onStateChange = { state ->
                    timerViewModel.onEvent(
                        TimerEvent.ChangeTimerState(state)
                    )
                },
                state = timerViewModel.state.value.timerState,
                minutes = timerViewModel.state.value.minutes,
                seconds = timerViewModel.state.value.seconds
            )

            LazyColumn {
                items(tasksViewModel.tasks.value) { task ->
                    TaskItem(
                        task = task,
                        onCheckChange = {
                            tasksViewModel.onEvent(
                                TasksEvent.ToggleCheckTask(task)
                            )
                        },
                        onSelect = {
                            tasksViewModel.onEvent(
                                TasksEvent.OnTaskSelect(task)
                            )
                        }
                    )
                }
            }
        }
    }
}