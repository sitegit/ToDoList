package com.example.todolist.presentation.screen.to_do_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.usecase.GetToDoListUseCase
import com.example.todolist.presentation.util.toHour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val calendar: Calendar
) : ViewModel() {

    private val _state = MutableStateFlow<ToDoListScreenState>(ToDoListScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        val currentDate = System.currentTimeMillis()
        loadToDoList(currentDate)
    }

    fun loadToDoList(date: Long) {
        val dayStart = getStartOfDay(date)
        val dayFinish = getEndOfDay(date)
        viewModelScope.launch {
            getToDoListUseCase(dayStart, dayFinish).collect {
                _state.value = ToDoListScreenState.Content(toDoList = it)
            }
        }
    }

    fun getTime(timeMillis: Long): Int {
        val calendarTime = calendar.apply {
            time = Date(timeMillis)
        }
        return calendarTime.toHour()
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val startDay = calendar.apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return startDay.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val finishDay = calendar.apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MILLISECOND, -1)
        }
        return finishDay.timeInMillis
    }
}