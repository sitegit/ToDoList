package com.example.todolist.presentation.screen.detail

import com.example.todolist.domain.ToDoEntity

sealed class DetailScreenState {

    data object Initial : DetailScreenState()

    data class Content(val toDo: ToDoEntity) : DetailScreenState()
}