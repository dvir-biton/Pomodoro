package com.fylora.pomodorotimer.data.entity

import androidx.room.Entity

@Entity
data class TaskEntity(
    val title: String,
    val workTime: Long,
    val dueDate: Long,
    val totalSessions: Int,
    val currentSessions: Int
)
