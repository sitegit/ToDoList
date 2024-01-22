package com.example.todolist.domain.usecase

import android.util.Log
import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(
    private val repository: ToDoRepository
) {

    operator fun invoke(dayStart: Long, dayEnd: Long): Flow<List<ToDoEntity>> =
        repository.getTasksForDay(dayStart, dayEnd)
            .onEach { tasks ->
                Log.i("GetToDoListUseCase", "Fetched ${tasks.size} tasks for period: $dayStart - $dayEnd")
                tasks.forEach { task ->
                    Log.i("GetToDoListUseCase", "Task: ${task.id}, Name: ${task.name}, Start: ${task.startTime}, End: ${task.finishTime}")
                }
            }
}
