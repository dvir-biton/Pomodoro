package com.fylora.pomodorotimer.presentation.util

sealed interface UiEvent {
    data class ShowSnackBar(val message: String): UiEvent
}