package com.example.todolist.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import com.example.todolist.presentation.screen.to_do_list.ToDoListScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow<DetailScreenState>(DetailScreenState.Initial)
    val state = _state.asStateFlow()

    fun getTask(id: Int) {
        viewModelScope.launch {
            _state.value = DetailScreenState.Content(repository.getTask(id))
        }
    }
}