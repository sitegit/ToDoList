package com.example.todolist.domain


interface ToDoRepository {

    suspend fun getTasksForDay(dayStart: Long, dayEnd: Long): List<ToDoEntity>

    suspend fun addTask(task: ToDoEntity)

    suspend fun getTask(id: Int): ToDoEntity
}