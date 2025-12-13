

package com.example.todo_mindora_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val priority: String,
    val category: String,
    val color: Int,
    val isCompleted: Boolean = false
)