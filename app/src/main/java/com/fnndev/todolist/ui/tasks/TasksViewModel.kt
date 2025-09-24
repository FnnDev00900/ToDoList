package com.fnndev.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.fnndev.todolist.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    val taskList = repository.getAllTasks()

    fun onEvent(event: TasksScreenEvents) {
        when (event) {

            TasksScreenEvents.OnAddTaskClick -> TODO()

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
}
