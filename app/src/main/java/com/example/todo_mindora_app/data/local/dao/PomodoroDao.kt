package com.example.todo_mindora_app.data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo_mindora_app.data.local.entity.PomodoroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PomodoroEntity)

    @Query("SELECT SUM(durationMinutes) FROM pomodoro_sessions WHERE date = :date AND type = 'Focus'")
    fun getTotalFocusMinutes(date: String): Flow<Int?>

    @Query("SELECT * FROM pomodoro_sessions ORDER BY id DESC")
    fun getAllSessions(): Flow<List<PomodoroEntity>>
}