package com.example.todolist.presentation.screen.detail

import com.example.todolist.data.local.model.ToDoDb


sealed class DetailScreenState {

    data object Initial : DetailScreenState()

    data class Content(val toDo: ToDoDb) : DetailScreenState()
}