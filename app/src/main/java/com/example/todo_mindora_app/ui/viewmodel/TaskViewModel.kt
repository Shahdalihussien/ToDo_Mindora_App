package com.example.todo_mindora_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_mindora_app.data.local.AppDatabase
import com.example.todo_mindora_app.data.local.entity.Task
import com.example.todo_mindora_app.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = TaskRepository(db.taskDao())

    val allTasks: StateFlow<List<Task>> =
        repository.getAllTasks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val inProgressTasks: StateFlow<List<Task>> =
        repository.getInProgressTasks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // مثال بسيط لحساب progress اليومي (ممكن نطوره)
    val todayProgress: StateFlow<Float> =
        allTasks.map { tasks ->
            if (tasks.isEmpty()) 0f
            else {
                val completed = tasks.count { it.isCompleted }
                completed.toFloat() / tasks.size.toFloat()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    fun addTask(
        title: String,
        description: String? = null,
        project: String? = "General"
    ) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val task = Task(
                title = title.trim(),
                description = description,
                project = project
            )
            repository.addTask(task)
        }
    }

    fun markTaskCompleted(taskId: Long) {
        viewModelScope.launch {
            repository.markCompleted(taskId)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
