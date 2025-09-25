package com.fnndev.todolist.ui.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.fnndev.todolist.navigation.Screens
import com.fnndev.todolist.utils.UiEvents

@Composable
fun TasksScreen(navController: NavController, viewModel: TasksViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.Navigate -> navController.navigate(Screens.AddEditTaskScreen.route)
                else -> Unit
            }
        }
    }

    val listTask = viewModel.taskList.collectAsState(initial = emptyList())

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(TasksScreenEvents.OnAddTaskClick)
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 2.dp,
                    bottom = innerPadding.calculateBottomPadding() + 2.dp
                )
        ) {
            items(items = listTask.value) {
                TaskItem(task = it, onEvent = viewModel::onEvent)
            }
        }
    }
}