package com.example.todolist.data.local.repository

import com.example.todolist.data.local.model.Task
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getTasksForDay(dayStart: Long): Flow<List<Task>>

    suspend fun addTask(task: Task)
}