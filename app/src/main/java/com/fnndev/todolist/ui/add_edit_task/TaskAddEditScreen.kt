package com.fnndev.todolist.ui.add_edit_task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.fnndev.todolist.R
import com.fnndev.todolist.utils.UiEvents

@Composable
fun TaskAddEditScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskAddEditViewModel = hiltViewModel()
) {
    LaunchedEffect(taskId) {
        viewModel.updateTaskId(taskId)
    }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { events ->
            when (events) {
                is UiEvents.PopBackStack -> {
                    navController.popBackStack()
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 2.dp,
                    bottom = innerPadding.calculateBottomPadding() + 2.dp,
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 2.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 2.dp
                ),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = viewModel.titleState.value,
                    onValueChange = {
                        viewModel.onEvent(TaskAddEditEvents.OnTitleChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(0.95f),
                    label = {
                        Text(text = "Title")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    maxLines = 1,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_title), contentDescription = ""
                        )
                    }
                )
                OutlinedTextField(
                    value = viewModel.descriptionState.value,
                    onValueChange = {
                        viewModel.onEvent(TaskAddEditEvents.OnDescriptionChange(it))
                    },
                    modifier = Modifier.fillMaxWidth(0.95f),
                    label = {
                        Text(text = "Description")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = false,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_note), contentDescription = ""
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    viewModel.onEvent(TaskAddEditEvents.OnSaveTask)
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
