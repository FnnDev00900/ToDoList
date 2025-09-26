package com.fnndev.todolist.ui.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fnndev.todolist.models.Task

@Composable
fun TaskItem(task: Task, onEvent: (TasksScreenEvents) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clickable(
                onClick = {
                    onEvent(TasksScreenEvents.OnTaskClick(task))
                }
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(0.95f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { isChecked ->
                        onEvent(TasksScreenEvents.OnIsDoneChange(task.copy(isCompleted = isChecked)))
                    }
                )

                Text(text = task.title, fontSize = 20.sp, color = Color.Black)

            }
            Row(
                modifier = Modifier.fillMaxWidth(0.95f).padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = task.description ?: "", fontSize = 14.sp, color = Color.Gray)
                IconButton(
                    onClick = {
                        onEvent(TasksScreenEvents.OnDeleteTask(task))
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                }
            }
        }
    }
}