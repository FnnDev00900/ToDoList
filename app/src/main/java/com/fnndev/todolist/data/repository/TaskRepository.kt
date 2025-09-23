package com.fnndev.todolist.data.repository

import com.fnndev.todolist.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Task?
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
}