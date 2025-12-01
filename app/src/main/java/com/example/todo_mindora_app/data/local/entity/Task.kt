package com.example.todo_mindora_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val project: String? = "General",
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
