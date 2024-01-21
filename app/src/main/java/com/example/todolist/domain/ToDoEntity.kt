package com.example.todolist.domain

import java.util.Calendar
import java.util.Date

data class ToDoEntity(
    val startTime: Long,
    val finishTime: Long,
    val name: String,
    val description: String,
    val id: Int = 0
) {

    val startDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@ToDoEntity.startTime)
            }
        }

    val finishDate: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@ToDoEntity.finishTime)
            }
        }
}
