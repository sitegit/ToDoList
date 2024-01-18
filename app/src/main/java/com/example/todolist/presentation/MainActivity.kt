package com.example.todolist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todolist.data.local.model.Task
import com.example.todolist.data.local.repository.ToDoRepositoryImpl
import com.example.todolist.presentation.ui.theme.ToDoListTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ToDoRepositoryImpl(this)

        /*GlobalScope.launch {
            repository.addTask(
                Task(
                    id = 2,
                    dateStart = 147600000,
                    dateFinish = System.currentTimeMillis(),
                    name = "ToDoD",
                    description = "ToDoD"
                )
            )
        }*/

        val listToDo = repository.getTasksForDay(147600000)

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
}

