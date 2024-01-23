package com.fylora.pomodorotimer.domain.repository

import com.fylora.pomodorotimer.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(task: Task)
}