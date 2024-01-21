package com.example.todolist.presentation.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.usecase.AddToDoUseCase
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class AddToDoViewModel @Inject constructor(
    private val addToDoUseCase: AddToDoUseCase,
    private val calendar: Calendar
) : ViewModel() {

    fun addTask(name: String, day: Long, times: Pair<Int, Int>, description: String) {
        viewModelScope.launch {
            val startTime = getSelectedTime(day, times.first)
            val finishTime = getSelectedTime(day, times.second)
            val toDoItem = ToDoEntity(
                name = name,
                startTime = startTime,
                finishTime = finishTime,
                description = description
            )
            addToDoUseCase(toDoItem)
        }
    }

    private fun getSelectedTime(date: Long, hour: Int): Long {
        val time = calendar.apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return time.timeInMillis
    }
}