package com.fnndev.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fnndev.todolist.models.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {
    abstract val dao: TaskDao
}