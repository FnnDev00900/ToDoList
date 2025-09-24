package com.fnndev.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fnndev.todolist.ui.tasks.TasksScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.TasksScreen.route) {
        composable(route = Screens.TasksScreen.route) { TasksScreen() }
        composable(route = Screens.AddEditTaskScreen.route) {}
    }
}