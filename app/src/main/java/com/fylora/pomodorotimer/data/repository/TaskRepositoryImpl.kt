package com.fylora.pomodorotimer.data.repository

import com.fylora.pomodorotimer.data.local.TaskDao
import com.fylora.pomodorotimer.data.mappers.toEntity
import com.fylora.pomodorotimer.data.mappers.toModel
import com.fylora.pomodorotimer.domain.model.InvalidTaskException
import com.fylora.pomodorotimer.domain.model.Task
import com.fylora.pomodorotimer.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { taskList ->
            taskList.map { task -> task.toModel() }
        }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)?.toModel()
    }

    override suspend fun upsertTask(task: Task) {
        if(task.title.isBlank())
            throw InvalidTaskException("The title cannot be blank")
        if(task.dueDate.isBefore(LocalDate.now()))
            throw InvalidTaskException("The due date cannot be in the past")
        if(task.workTime.toSecondOfDay() == 0)
            throw InvalidTaskException("The work time cannot be 0")

        dao.upsertTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }
}