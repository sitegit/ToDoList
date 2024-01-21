package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import javax.inject.Inject

class AddToDoUseCase  @Inject constructor(
    private val repository: ToDoRepository
) {

    suspend operator fun invoke(toDo: ToDoEntity) {
        repository.addTask(toDo)
    }
}