package com.example.todolist.domain

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getTasksForDay(dayStart: Long, dayEnd: Long): Flow<List<ToDoEntity>>

    suspend fun addTask(task: ToDoEntity)

    suspend fun getTask(id: Int): ToDoEntity
}