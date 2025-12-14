package com.example.todo_mindora_app.data.repository

import com.example.todo_mindora_app.data.local.Dao.TaskDao
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    /* ---------------- GET ---------------- */

    val allTasks: Flow<List<TaskEntity>> =
        taskDao.getAllTasks()

    fun getTasksForDate(date: String): Flow<List<TaskEntity>> =
        taskDao.getTasksByDate(date)

    suspend fun getTaskById(taskId: Int): TaskEntity? =
        taskDao.getTaskById(taskId)

    /* ---------------- ADD ---------------- */

    suspend fun addTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    /* ---------------- UPDATE ---------------- */

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    /* ---------------- DELETE ---------------- */

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}
