package com.fnndev.todolist.ui.add_edit_task

sealed class TaskAddEditEvents {
    data class OnTitleChange(val title: String): TaskAddEditEvents()
    data class OnDescriptionChange(val description: String): TaskAddEditEvents()
    object OnSaveTask: TaskAddEditEvents()
}