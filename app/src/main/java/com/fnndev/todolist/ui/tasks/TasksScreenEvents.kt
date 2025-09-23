package com.fnndev.todolist.ui.tasks

import com.fnndev.todolist.models.Task

sealed class TasksScreenEvents {
    data class OnIsDoneChange(val task: Task): TasksScreenEvents()
    data class OnDeleteTask(val task: Task): TasksScreenEvents()
    data class OnTaskClick(val task: Task): TasksScreenEvents()
    object OnAddTaskClick: TasksScreenEvents()
}