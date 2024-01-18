package com.example.todolist.data.local.repository

import android.content.Context
import com.example.todolist.data.local.database.ToDoDatabase
import com.example.todolist.data.local.model.Task
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(private val context: Context) : ToDoRepository {

    private val database = ToDoDatabase.getInstance(context)
    private val toDoDao = database.toDoDao()

    override fun getTasksForDay(dayStart: Long): Flow<List<Task>> = toDoDao
        .getTasksForDay(dayStart)

    override suspend fun addTask(task: Task) {
        toDoDao.insertTask(task)
    }
}