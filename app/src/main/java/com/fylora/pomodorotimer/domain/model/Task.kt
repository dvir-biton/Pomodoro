package com.fylora.pomodorotimer.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val title: String,
    val workTime: LocalTime,
    val dueDate: LocalDate,
    val totalSessions: Double,
    val currentSessions: Double = 0.0,
    val isCompleted: Boolean = false,
    val isSelected: Boolean = false,

    val id: Int? = null
)