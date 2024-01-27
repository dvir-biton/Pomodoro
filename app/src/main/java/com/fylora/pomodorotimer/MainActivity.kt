package com.fylora.pomodorotimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.pomodorotimer.domain.model.Task
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerEvent
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerScreenViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.TaskItem
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header.Header
import com.fylora.pomodorotimer.ui.theme.PomodoroTimerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroTimerTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    val viewModel: TimerScreenViewModel = hiltViewModel()
                    Header(
                        isTimerRunning = viewModel.state.value.isTimerRunning,
                        onStartStop = {
                            viewModel.onEvent(TimerEvent.StartStopTimer)
                        },
                        onStateChange = {
                            viewModel.onEvent(
                                TimerEvent.ChangeTimerState(it)
                            )
                        },
                        state = viewModel.state.value.timerState,
                        minutes = viewModel.state.value.minutes,
                        seconds = viewModel.state.value.seconds
                    )

                    TaskItem(
                        task = Task(
                            title = "this is the task",
                            workTime = LocalTime.now(),
                            dueDate = LocalDate.now().plusDays(1),
                            totalSessions = 4.0,
                        ),
                        onCheckChange = {}
                    )
                }
            }
        }
    }
}
