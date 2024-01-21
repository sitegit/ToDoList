package com.example.todolist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.todolist.presentation.navigation.AppNavGraph
import com.example.todolist.presentation.navigation.Screen
import com.example.todolist.presentation.screen.add.AddToDoScreen
import com.example.todolist.presentation.screen.detail.DetailScreen
import com.example.todolist.presentation.screen.to_do_list.ToDoListScreen
import com.example.todolist.presentation.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoListTheme {
                val navHostController = rememberNavController()

                AppNavGraph(
                    navHostController = navHostController,
                    toDoListScreenContent = {
                        ToDoListScreen(
                            onClickedCard = {
                                navHostController.navigate(Screen.Detail.getRouteWithArgs(it))
                            },
                            onClickAddToDoButton = {
                                navHostController.navigate(Screen.AddToDo.getRouteWithArgs(it))
                            }
                        )
                    },
                    detailScreenContent = {
                        DetailScreen(it) { navHostController.popBackStack() }
                    },
                    addScreenContent = {
                        AddToDoScreen(it) { navHostController.popBackStack() }
                    }
                )
            }
        }
    }
}

