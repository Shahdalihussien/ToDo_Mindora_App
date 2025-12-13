package com.example.todo_mindora_app.data.repository

import com.example.todo_mindora_app.data.local.Dao.PomodoroDao
import com.example.todo_mindora_app.data.local.entity.PomodoroEntity
import kotlinx.coroutines.flow.Flow

class PomodoroRepository(private val pomodoroDao: PomodoroDao) {

    suspend fun saveSession(session: PomodoroEntity) {
        pomodoroDao.insertSession(session)
    }

    fun getTodayFocusMinutes(date: String): Flow<Int?> {
        return pomodoroDao.getTotalFocusMinutes(date)
    }
}