package com.example.todo_mindora_app.data.repository

import com.example.todo_mindora_app.data.local.Dao.TaskDao
import com.example.todo_mindora_app.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getInProgressTasks(): Flow<List<Task>> = taskDao.getInProgressTasks()

    suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun markCompleted(taskId: Long) {
        taskDao.markCompleted(taskId)
    }
}
