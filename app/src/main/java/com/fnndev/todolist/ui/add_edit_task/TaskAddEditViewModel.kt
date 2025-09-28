package com.fnndev.todolist.ui.add_edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnndev.todolist.data.repository.TaskRepository
import com.fnndev.todolist.models.Task
import com.fnndev.todolist.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddEditViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private var selectedTask: Task? = null

    private val _taskId = MutableStateFlow(-1)
    val taskId = _taskId.asStateFlow()


    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val titleState = mutableStateOf("")
    val descriptionState = mutableStateOf("")

    fun updateTaskId(taskId: Int) {
        _taskId.value = taskId
        getTaskById(taskId)
    }

    fun getTaskById(taskId: Int) {
        if (taskId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getTaskById(taskId).collect { task ->
                    selectedTask = task
                    task?.let {
                        titleState.value = it.title
                        descriptionState.value = it.description ?: ""
                    }
                }
            }
        }
    }

    fun onEvent(event: TaskAddEditEvents) {
        when (event) {
            is TaskAddEditEvents.OnDescriptionChange -> descriptionState.value = event.description
            is TaskAddEditEvents.OnTitleChange -> titleState.value = event.title
            TaskAddEditEvents.OnSaveTask -> {
                if (_taskId.value == -1) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val task = Task(
                            id = 0,
                            title = titleState.value,
                            description = descriptionState.value
                        )
                        repository.upsertTask(task)
                    }
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        val task = Task(
                            id = _taskId.value,
                            title = titleState.value,
                            description = descriptionState.value,
                            isCompleted = selectedTask!!.isCompleted
                        )
                        repository.upsertTask(task)
                    }
                }
                sendUiEvent(UiEvents.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}