package com.example.todolist.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todolist.data.local.model.ToDoDb
import com.example.todolist.navigation.Screen.Companion.KEY_TO_DO_ITEM

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    toDoListScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable (Int) -> Unit,
    addScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ToDoList.route,
    ) {

        composable(Screen.ToDoList.route) {
            toDoListScreenContent()
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(KEY_TO_DO_ITEM) {
                    type = NavType.IntType
                }
            )
        ) {
            val taskId = it.arguments?.getInt(KEY_TO_DO_ITEM) ?: 0
            detailScreenContent(taskId)
        }
        composable(Screen.AddToDo.route) {
            addScreenContent()
        }
    }
}