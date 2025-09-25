package com.fnndev.todolist.data.repository

import com.fnndev.todolist.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Int): Flow<Task?>
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
}