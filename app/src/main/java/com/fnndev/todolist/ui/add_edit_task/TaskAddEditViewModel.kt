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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddEditViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val titleState = mutableStateOf("")
    val descriptionState = mutableStateOf("")

    fun onEvent(event: TaskAddEditEvents) {
        when (event) {
            is TaskAddEditEvents.OnDescriptionChange -> descriptionState.value = event.description
            is TaskAddEditEvents.OnTitleChange -> titleState.value = event.title
            TaskAddEditEvents.OnSaveTask -> {
                if (titleState.value.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val task = Task(
                            title = titleState.value,
                            description = descriptionState.value
                        )
                        repository.upsertTask(task)
                        sendUiEvent(UiEvents.PopBackStack)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}