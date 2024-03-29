package com.example.todolist.presentation.screen.to_do_list

import com.example.todolist.domain.ToDoEntity

sealed class ToDoListScreenState {

    data object Initial : ToDoListScreenState()

    data object Loading : ToDoListScreenState()

    data class Content(val toDoList: List<ToDoEntity>) : ToDoListScreenState()
}
