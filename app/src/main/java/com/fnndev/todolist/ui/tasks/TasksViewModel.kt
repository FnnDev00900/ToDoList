package com.fnndev.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnndev.todolist.data.repository.TaskRepository
import com.fnndev.todolist.models.Task
import com.fnndev.todolist.navigation.Screens
import com.fnndev.todolist.utils.UiEvents
import com.fnndev.todolist.utils.UiEvents.Navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {

    val taskList: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val filteredListTask: StateFlow<List<Task>> = combine(_searchText, taskList) { query, tasks ->
        if (query.isEmpty()) tasks
        else tasks.filter { it.title.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEvent(event: TasksScreenEvents) {
        when (event) {

            TasksScreenEvents.OnAddTaskClick -> {
                sendUiEvent(Navigate(Screens.AddEditTaskScreen.withArgs("-1")))
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

            is TasksScreenEvents.OnTaskClick -> {
                sendUiEvent(Navigate(Screens.AddEditTaskScreen.withArgs(event.task.id.toString())))
            }

            is TasksScreenEvents.OnSearchTextChange -> _searchText.value = event.text
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
