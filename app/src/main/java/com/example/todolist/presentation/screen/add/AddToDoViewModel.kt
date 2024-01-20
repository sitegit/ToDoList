package com.example.todolist.presentation.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddToDoViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    fun addTask(toDoItem: ToDoDb) {
        viewModelScope.launch {
            repository.addTask(toDoItem)
        }
    }
}