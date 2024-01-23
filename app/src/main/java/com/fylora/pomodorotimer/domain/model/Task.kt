package com.fylora.pomodorotimer.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val title: String,
    val workTime: LocalTime,
    val dueDate: LocalDate,
    val totalSessions: Int,
    val currentSessions: Int,

    val id: Int? = null
)
