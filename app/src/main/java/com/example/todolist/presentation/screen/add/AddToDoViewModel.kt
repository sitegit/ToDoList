package com.example.todolist.presentation.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.usecase.AddToDoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddToDoViewModel @Inject constructor(
    private val addToDoUseCase: AddToDoUseCase
) : ViewModel() {

    fun addTask(toDoItem: ToDoEntity) {
        viewModelScope.launch {
            addToDoUseCase(toDoItem)
        }
    }
}