package com.fylora.pomodorotimer.data.mappers

import com.fylora.pomodorotimer.data.entity.TaskEntity
import com.fylora.pomodorotimer.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        title = title,
        workTime = workTime.toSecondOfDay().toLong(),
        dueDate = dueDate.toEpochDay(),
        totalSessions = totalSessions,
        currentSessions = currentSessions
    )
}

fun TaskEntity.toModel(): Task {
    return Task(
        title = title,
        workTime = LocalTime.ofSecondOfDay(workTime),
        dueDate = LocalDate.ofEpochDay(dueDate),
        totalSessions = totalSessions,
        currentSessions = currentSessions
    )
}