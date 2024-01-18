package com.example.todolist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import com.example.todolist.presentation.task.TaskListScreen
import com.example.todolist.presentation.ui.theme.ToDoListTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ToDoRepositoryImpl(this)

        val timestamp = 1705536000000L // Ваше значение в миллисекундах
        val startOfDay = getStartOfDay(timestamp)
        val endOfDay = getEndOfDay(timestamp)

        /*GlobalScope.launch {
            repository.addTask(
                ToDoDb(
                    id = 6,
                    dateStart = 1705527660000,
                    dateFinish = 1705530189999,
                    name = "Tuest",
                    description = "Tuest"
                )
            )
        }*/

        val listToDo = repository.getTasksForDay(startOfDay, endOfDay)

       GlobalScope.launch {
            listToDo.collect {
                val newList = it.map { task ->
                    val startDate = getFormattedDate(task.startDate) + "\n" + getFormattedTime(task.startDate)
                    val finishDate = getFormattedDate(task.finishDate) + "\n" + getFormattedTime(task.finishDate)

                    "${task.id}\n$startDate\n$finishDate\n${task.name}\n${task.description}\n"
                }

                Log.i("MyTag", newList.toString())
            }
        }

        setContent {
            ToDoListTheme {
                TaskListScreen()
            }
        }
    }

    private fun getFormattedDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return String.format("%02d.%02d.%04d", day, month, year)
    }

    private fun getFormattedTime(calendar: Calendar): String {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return String.format("%02d:00", hour)
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

