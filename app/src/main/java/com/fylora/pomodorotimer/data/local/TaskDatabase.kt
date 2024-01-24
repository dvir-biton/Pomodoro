package com.fylora.pomodorotimer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fylora.pomodorotimer.data.entity.TaskEntity

@Database(
    version = 1,
    entities = [TaskEntity::class],
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {
    abstract val dao: TaskDao
}