package com.example.todolist.presentation.to_do_list

import com.example.todolist.data.local.model.ToDoDb

sealed class ToDoListScreenState {

    data object Initial : ToDoListScreenState()

    data object Loading : ToDoListScreenState()

    data class Error(val message: String? = null) : ToDoListScreenState()

    data class Content(val toDoList: List<ToDoDb>) : ToDoListScreenState()
}
