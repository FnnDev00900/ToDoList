package com.fnndev.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnndev.todolist.data.repository.TaskRepository
import com.fnndev.todolist.navigation.Screens
import com.fnndev.todolist.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    val taskList = repository.getAllTasks()

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TasksScreenEvents) {
        when (event) {

            TasksScreenEvents.OnAddTaskClick -> {
                sendUiEvent(UiEvents.Navigate(Screens.AddEditTaskScreen.route))
            }

            is TasksScreenEvents.OnDeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteTask(event.task)
                }
            }

            is TasksScreenEvents.OnIsDoneChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.upsertTask(event.task)
                }
            }

            is TasksScreenEvents.OnTaskClick -> TODO()
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
