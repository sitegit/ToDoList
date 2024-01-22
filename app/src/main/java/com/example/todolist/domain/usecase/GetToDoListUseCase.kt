package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(
    private val repository: ToDoRepository
) {

    suspend operator fun invoke(dayStart: Long, dayEnd: Long): List<ToDoEntity> =
        repository.getTasksForDay(dayStart, dayEnd)
}
