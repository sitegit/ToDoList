package com.example.todolist.data.local.repository

import com.example.todolist.data.local.database.ToDoDao
import com.example.todolist.data.local.model.ToDoDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoRepository {

    override fun getTasksForDay(dayStart: Long, dayEnd: Long): Flow<List<ToDoDb>> = toDoDao
        .getTasksForDay(dayStart, dayEnd)

    override suspend fun addTask(task: ToDoDb) {
        toDoDao.insertTask(task)
    }
}