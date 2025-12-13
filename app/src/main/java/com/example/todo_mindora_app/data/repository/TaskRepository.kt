package com.example.todo_mindora_app.data.repository

import com.example.todo_mindora_app.data.local.Dao.TaskDao
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()
    fun getTasksForDate(date: String): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDate(date)
    }

    suspend fun addTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }
}