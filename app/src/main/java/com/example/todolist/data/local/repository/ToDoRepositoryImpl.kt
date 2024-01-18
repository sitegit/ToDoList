package com.example.todolist.data.local.repository

import android.content.Context
import com.example.todolist.data.local.database.ToDoDatabase
import com.example.todolist.data.local.model.ToDoDb
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(private val context: Context) : ToDoRepository {

    private val database = ToDoDatabase.getInstance(context)
    private val toDoDao = database.toDoDao()

    override fun getTasksForDay(dayStart: Long, dayEnd: Long): Flow<List<ToDoDb>> = toDoDao
        .getTasksForDay(dayStart, dayEnd)

    override suspend fun addTask(task: ToDoDb) {
        toDoDao.insertTask(task)
    }
}