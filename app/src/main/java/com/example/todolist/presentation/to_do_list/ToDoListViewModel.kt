package com.example.todolist.presentation.to_do_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Initial)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = ListScreenState.Error(throwable.message)
        Log.i("MyTag", throwable.message.toString())
    }

    init {
        val currentDate = System.currentTimeMillis()

        loadToDoList(currentDate)
    }

    fun loadToDoList(date: Long) {
        //_state.value = ToDoListScreenState.Loading
        val dayStart = getStartOfDay(date)
        val dayFinish = getEndOfDay(date)
        viewModelScope.launch(exceptionHandler) {
            repository.getTasksForDay(dayStart, dayFinish).collect {
                _state.value = ListScreenState.Content(toDoList = it)
                Log.i("MyTag", state.value.toString())
            }
        }
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MILLISECOND, -1)
        }
        return calendar.timeInMillis
    }
}