package com.example.todolist.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    suspend fun getDetailInfo(id: Int): ToDoDb = repository.getTask(id)
}