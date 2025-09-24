package com.fnndev.todolist.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    repository.upsertTask(event.task.copy(isCompleted = !event.task.isCompleted))
                }
            }

            is TasksScreenEvents.OnTaskClick -> TODO()
        }
    }
}
