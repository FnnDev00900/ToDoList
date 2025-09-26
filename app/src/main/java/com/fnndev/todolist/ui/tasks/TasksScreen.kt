package com.fnndev.todolist.ui.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.fnndev.todolist.utils.UiEvents

@SuppressLint("FlowOperatorInvokedInComposition", "RememberInComposition")
@Composable
fun TasksScreen(navController: NavController, viewModel: TasksViewModel = hiltViewModel()) {

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.Navigate -> navController.navigate(event.route)
                else -> Unit
            }
        }
    }

    val filteredListTask = viewModel.filteredListTask.collectAsState()
    val searchText = viewModel.searchText.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(TasksScreenEvents.OnAddTaskClick)
                focusManager.clearFocus()
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
                .clickable(
                    indication = null, interactionSource = MutableInteractionSource(),
                    onClick = { focusManager.clearFocus() })
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(0.95f),
                        text = searchText.value,
                        onTextChange = viewModel::onEvent
                    )
                }

            }
            items(items = filteredListTask.value) {
                TaskItem(task = it, onEvent = viewModel::onEvent)
            }
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (TasksScreenEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChange(TasksScreenEvents.OnSearchTextChange(it))
            2
        },
        modifier = modifier,
        label = {
            Text(text = "Search")
        },
        singleLine = true,
        maxLines = 1,
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        )
    )
}