package com.fylora.pomodorotimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerEvent
import com.fylora.pomodorotimer.presentation.timer_screen.timer.TimerScreenViewModel
import com.fylora.pomodorotimer.presentation.timer_screen.timer.components.header.Header
import com.fylora.pomodorotimer.ui.theme.PomodoroTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroTimerTheme {
                Box(
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
                }
            }
        }
    }
}
