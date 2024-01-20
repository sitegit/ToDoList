package com.example.todolist.presentation.to_do_list

import com.example.todolist.data.local.model.ToDoDb

sealed class ListScreenState {

    data object Initial : ListScreenState()

    //data object Loading : ToDoListScreenState()

    data class Error(val message: String? = null) : ListScreenState()

    data class Content(val toDoList: List<ToDoDb>) : ListScreenState()
}
