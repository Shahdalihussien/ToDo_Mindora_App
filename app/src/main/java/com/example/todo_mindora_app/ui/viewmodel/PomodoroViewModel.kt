package com.example.todo_mindora_app.ui.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_mindora_app.data.local.database.AppDatabase
import com.example.todo_mindora_app.data.local.entity.PomodoroEntity
import com.example.todo_mindora_app.data.repository.PomodoroRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PomodoroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PomodoroRepository

    private val FOCUS_TIME = 25 * 60 * 1000L
    private val BREAK_TIME = 5 * 60 * 1000L

    var currentTime by mutableLongStateOf(FOCUS_TIME)
    var isTimerRunning by mutableStateOf(false)
    var isFocusMode by mutableStateOf(true)
    var progress by mutableFloatStateOf(1f)

    private var timer: CountDownTimer? = null
    private var totalTime = FOCUS_TIME

    init {
        val db = AppDatabase.getDatabase(application)
        repository = PomodoroRepository(db.pomodoroDao())
    }

    fun startTimer() {
        if (timer == null) {
            timer = object : CountDownTimer(currentTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    currentTime = millisUntilFinished
                    progress = currentTime.toFloat() / totalTime.toFloat()
                }

                override fun onFinish() {
                    isTimerRunning = false
                    progress = 0f
                    saveCompletedSession()
                    switchMode()
                }
            }.start()
            isTimerRunning = true
        }
    }

    private fun saveCompletedSession() {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            val nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val type = if (isFocusMode) "Focus" else "Break"
            val duration = if (isFocusMode) 25 else 5

            val session = PomodoroEntity(
                date = today,
                startTime = nowTime,
                durationMinutes = duration,
                type = type
            )
            repository.saveSession(session)
        }
    }

    fun pauseTimer() {
        timer?.cancel()
        timer = null
        isTimerRunning = false
    }

    fun resetTimer() {
        pauseTimer()
        currentTime = if (isFocusMode) FOCUS_TIME else BREAK_TIME
        totalTime = currentTime
        progress = 1f
    }

    fun switchMode() {
        pauseTimer()
        isFocusMode = !isFocusMode
        currentTime = if (isFocusMode) FOCUS_TIME else BREAK_TIME
        totalTime = currentTime
        progress = 1f
    }
}