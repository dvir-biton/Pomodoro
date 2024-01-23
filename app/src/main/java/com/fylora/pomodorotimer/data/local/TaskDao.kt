package com.fylora.pomodorotimer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fylora.pomodorotimer.core.Consts.TASK_TABLE_NAME
import com.fylora.pomodorotimer.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM $TASK_TABLE_NAME")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}