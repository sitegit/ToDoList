package com.example.todolist.data.local.repository

import com.example.todolist.data.local.model.ToDoDb
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getTasksForDay(dayStart: Long, dayEnd: Long): Flow<List<ToDoDb>>

    suspend fun addTask(task: ToDoDb)

    suspend fun getTask(id: Int): ToDoDb
}