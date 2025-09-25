package com.fnndev.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fnndev.todolist.ui.add_edit_task.TaskAddEditScreen
import com.fnndev.todolist.ui.tasks.TasksScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.TasksScreen.route) {
        composable(route = Screens.TasksScreen.route) {
            TasksScreen(navController)
        }
        composable(route = Screens.AddEditTaskScreen.route) {
            TaskAddEditScreen()
        }
    }
}