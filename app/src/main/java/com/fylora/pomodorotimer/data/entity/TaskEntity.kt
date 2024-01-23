package com.fylora.pomodorotimer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fylora.pomodorotimer.core.Consts.TASK_TABLE_NAME

@Entity(tableName = TASK_TABLE_NAME)
data class TaskEntity(
    val title: String,
    val workTime: Long,
    val dueDate: Long,
    val totalSessions: Int,
    val currentSessions: Int,

    @PrimaryKey
    val id: Int? = null
)
