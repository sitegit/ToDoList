package com.example.todolist.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.usecase.GetToDoInfoUseCase
import com.example.todolist.presentation.util.toHour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getToDoInfoUseCase: GetToDoInfoUseCase,
    private val calendar: Calendar
) : ViewModel() {

    private val _state = MutableStateFlow<DetailScreenState>(DetailScreenState.Initial)
    val state = _state.asStateFlow()

    fun getTask(id: Int) {
        viewModelScope.launch {
            _state.value = DetailScreenState.Content(getToDoInfoUseCase(id))
        }
    }

    fun getTime(timeMillis: Long): Int {
        val calendarTime = calendar.apply {
            time = Date(timeMillis)
        }
        return calendarTime.toHour()
    }
}