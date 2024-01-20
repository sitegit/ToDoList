package com.example.todolist.navigation

import com.example.todolist.data.local.model.ToDoDb

sealed class Screen(
    val route: String
) {

    data object ToDoList : Screen(ROUTE_TO_DO_LIST)

    data object Detail : Screen(ROUTE_DETAIL) {
        private const val ROUTE_FOR_ARGS = "detail"

        fun getRouteWithArgs(toDoItem: ToDoDb): String {
            return "$ROUTE_FOR_ARGS/${toDoItem.id}"
        }
    }

    data object AddToDo : Screen(ROUTE_ADD_TO_DO)

    companion object {
        const val KEY_TO_DO_ITEM = "key_to_do_item"

        const val ROUTE_TO_DO_LIST = "to_do_list"
        const val ROUTE_DETAIL = "detail/{$KEY_TO_DO_ITEM}"
        const val ROUTE_ADD_TO_DO = "add_to_do"
    }
}

