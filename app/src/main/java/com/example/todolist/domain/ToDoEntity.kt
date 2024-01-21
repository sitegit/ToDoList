package com.example.todolist.domain

data class ToDoEntity(
    val startTime: Long,
    val finishTime: Long,
    val name: String,
    val description: String,
    val id: Int = 0
)
