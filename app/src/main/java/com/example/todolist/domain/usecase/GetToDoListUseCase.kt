package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(
    private val repository: ToDoRepository
) {

    operator fun invoke(dayStart: Long, dayEnd: Long): Flow<List<ToDoEntity>> =
        repository.getTasksForDay(dayStart, dayEnd)
}