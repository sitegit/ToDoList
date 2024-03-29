package com.example.todolist.presentation.navigation

import com.example.todolist.domain.ToDoEntity

sealed class Screen(
    val route: String
) {

    data object ToDoList : Screen(ROUTE_TO_DO_LIST) {
        private const val ROUTE_FOR_ARGS = "to_do_list"

        fun getRouteWithArgs(timeMillis: Long): String {
            return "$ROUTE_FOR_ARGS/$timeMillis"
        }
    }

    data object Detail : Screen(ROUTE_DETAIL) {
        private const val ROUTE_FOR_ARGS = "detail"

        fun getRouteWithArgs(toDoItem: ToDoEntity): String {
            return "$ROUTE_FOR_ARGS/${toDoItem.id}"
        }
    }

    data object AddToDo : Screen(ROUTE_ADD_TO_DO) {

        private const val ROUTE_FOR_ARGS = "add_to_do"

        fun getRouteWithArgs(timeMillis: Long): String {
            return "$ROUTE_FOR_ARGS/$timeMillis"
        }
    }

    companion object {
        const val KEY_TO_DO_ITEM = "key_to_do_item"
        const val KEY_ADD_TO_DO = "key_add_to_do"
        const val KEY_TO_DO_LIST = "key_to_do_list"

        const val ROUTE_TO_DO_LIST = "to_do_list/{$KEY_TO_DO_LIST}"
        const val ROUTE_DETAIL = "detail/{$KEY_TO_DO_ITEM}"
        const val ROUTE_ADD_TO_DO = "add_to_do/{$KEY_ADD_TO_DO}"
    }
}

