package com.fnndev.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.TasksScreen.route) {
        composable(route = Screens.TasksScreen.route) {}
        composable(route = Screens.AddEditTaskScreen.route) {}
    }
}