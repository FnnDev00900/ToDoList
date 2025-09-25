package com.fnndev.todolist.navigation

sealed class Screens(val route: String) {
    object TasksScreen : Screens(route = "tasks_screen")
    object AddEditTaskScreen : Screens(route = "add_edit_task_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}