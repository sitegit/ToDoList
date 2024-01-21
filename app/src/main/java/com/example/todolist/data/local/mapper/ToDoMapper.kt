package com.example.todolist.data.local.mapper

import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.domain.ToDoEntity

fun ToDoEntity.toDbModel(): ToDoDb = ToDoDb(
    name = name,
    startTime = startTime,
    finishTime = finishTime,
    description = description
)

fun ToDoDb.toEntity(): ToDoEntity = ToDoEntity(
    name = name,
    startTime = startTime,
    finishTime = finishTime,
    description = description,
    id = id
)

fun List<ToDoDb>.toEntities(): List<ToDoEntity> = map { toDo ->
    toDo.toEntity()
}