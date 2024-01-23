package com.example.todolist.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todolist.presentation.navigation.Screen.Companion.KEY_ADD_TO_DO
import com.example.todolist.presentation.navigation.Screen.Companion.KEY_TO_DO_ITEM
import com.example.todolist.presentation.navigation.Screen.Companion.KEY_TO_DO_LIST

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    toDoListScreenContent: @Composable (Long) -> Unit,
    detailScreenContent: @Composable (Int) -> Unit,
    addScreenContent: @Composable (Long) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ToDoList.route,
    ) {

        composable(
            route = Screen.ToDoList.route,
            arguments = listOf(
                navArgument(KEY_TO_DO_LIST) {
                    type = NavType.LongType
                }
            )
        ) {
            val taskTimestampDay = it.arguments?.getLong(KEY_TO_DO_LIST) ?: 0
            toDoListScreenContent(taskTimestampDay)
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

        composable(
            route = Screen.AddToDo.route,
            arguments = listOf(
                navArgument(KEY_ADD_TO_DO) {
                    type = NavType.LongType
                }
            )
        ) {
            val taskTimestampDay = it.arguments?.getLong(KEY_ADD_TO_DO) ?: 0
            addScreenContent(taskTimestampDay)
        }
    }
}