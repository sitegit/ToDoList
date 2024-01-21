package com.example.todolist.data.local.repository

import com.example.todolist.data.local.database.ToDoDao
import com.example.todolist.data.local.mapper.toDbModel
import com.example.todolist.data.local.mapper.toEntities
import com.example.todolist.data.local.mapper.toEntity
import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoRepository {

    override fun getTasksForDay(dayStart: Long, dayEnd: Long): Flow<List<ToDoEntity>> = toDoDao
        .getTasksForDay(dayStart, dayEnd).map {
            it.toEntities()
        }

    override suspend fun addTask(task: ToDoEntity) {
        toDoDao.insertTask(task.toDbModel())
    }

    override suspend fun getTask(id: Int): ToDoEntity = toDoDao.getTask(id).toEntity()
}