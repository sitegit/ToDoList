package com.example.todolist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ToDoApplication
import com.example.todolist.ViewModelFactory
import com.example.todolist.presentation.to_do_list.ToDoListScreen
import com.example.todolist.presentation.to_do_list.ToDoListScreenState
import com.example.todolist.presentation.to_do_list.ToDoListViewModel
import com.example.todolist.presentation.ui.theme.ToDoListTheme
import java.util.Calendar
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (this.application as ToDoApplication).applicationComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            ToDoListTheme {

                val viewModel: ToDoListViewModel = viewModel(factory = viewModelFactory)
                val screenState = viewModel.state.collectAsState()

                when (val currentState = screenState.value) {
                    is ToDoListScreenState.Content -> {
                        ToDoListScreen(toDoList = currentState.toDoList) {
                            viewModel.loadToDoList(it)
                        }
                    }
                    is ToDoListScreenState.Error -> {
                        //Log.i("MyTag", "ToDoListScreenState.Error")
                    }
                    ToDoListScreenState.Initial -> {
                        //Log.i("MyTag", "ToDoListScreenState.Initial")
                    }

                }
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

