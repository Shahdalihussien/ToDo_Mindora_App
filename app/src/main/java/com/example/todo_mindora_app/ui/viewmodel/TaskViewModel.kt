package com.example.todo_mindora_app.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_mindora_app.data.local.database.AppDatabase
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = TaskRepository(db.taskDao())

    val allTasks = repository.allTasks

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    val tasks = _selectedDate.flatMapLatest { date ->
        repository.getTasksForDate(date.toString())
    }

    private val _daysInMonth = MutableStateFlow(getDaysCurrentMonth())
    val daysInMonth: StateFlow<List<LocalDate>> = _daysInMonth.asStateFlow()

    /* ---------------- DATE ---------------- */

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    /* ---------------- ADD ---------------- */

    fun addTask(
        title: String,
        desc: String,
        date: String,
        startTime: String,
        endTime: String,
        priority: String,
        category: String,
        color: Color
    ) {
        viewModelScope.launch {
            repository.addTask(
                TaskEntity(
                    title = title,
                    description = desc,
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    priority = priority,
                    category = category,
                    color = color.toArgb()
                )
            )
        }
    }

    /* ---------------- UPDATE ---------------- */

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun toggleTaskCompletion(task: TaskEntity) {
        updateTask(task.copy(isCompleted = !task.isCompleted))
    }

    /* ---------------- DELETE ---------------- */

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    /* ---------------- GET BY ID ---------------- */

    suspend fun getTaskById(taskId: Int): TaskEntity? {
        return repository.getTaskById(taskId)
    }

    /* ---------------- HELPERS ---------------- */

    private fun getDaysCurrentMonth(): List<LocalDate> {
        val yearMonth = YearMonth.now()
        return (1..yearMonth.lengthOfMonth())
            .map { yearMonth.atDay(it) }
    }
}
