package com.fylora.pomodorotimer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fylora.pomodorotimer.core.Consts.TASK_TABLE_NAME

@Entity(tableName = TASK_TABLE_NAME)
data class TaskEntity(
    val title: String,
    val workTime: Long,
    val dueDate: Long,
    val totalSessions: Double,
    val currentSessions: Double,
    val isCompleted: Boolean,
    val isSelected: Boolean,

    @PrimaryKey
    val id: Int? = null
)
