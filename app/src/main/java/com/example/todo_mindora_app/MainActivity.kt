package com.example.todo_mindora_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_mindora_app.ui.screens.auth.LoginScreen
import com.example.todo_mindora_app.ui.screens.auth.SignupScreen
import com.example.todo_mindora_app.ui.screens.home.HomeScreen
import com.example.todo_mindora_app.ui.screens.onboarding.OnboardingScreen
import com.example.todo_mindora_app.ui.screens.tasks.AddTaskScreen
import com.example.todo_mindora_app.ui.theme.ToDo_Mindora_AppTheme
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("mindora_prefs", MODE_PRIVATE)

        setContent {
            ToDo_Mindora_AppTheme {

                // هل شاف الـ Onboarding قبل كده؟
                var hasSeenOnboarding by remember {
                    mutableStateOf(prefs.getBoolean("has_seen_onboarding", false))
                }

                // true = Login screen, false = Signup screen
                var isLogin by remember { mutableStateOf(true) }

                var showHome by remember { mutableStateOf(false) }

                var showAddTask by remember { mutableStateOf(false) }

                val authViewModel: AuthViewModel = viewModel()
                val taskViewModel: TaskViewModel = viewModel()

                when {
                    // 1) Onboarding أول مرة بس
                    !hasSeenOnboarding -> {
                        OnboardingScreen(
                            onFinish = {
                                prefs.edit()
                                    .putBoolean("has_seen_onboarding", true)
                                    .apply()
                                hasSeenOnboarding = true
                            }
                        )
                    }

                    !showHome -> {
                        if (isLogin) {
                            LoginScreen(
                                authViewModel = authViewModel,
                                onNavigateToSignup = { isLogin = false },
                                onLoginSuccess = {
                                    showHome = true
                                }
                            )
                        } else {
                            SignupScreen(
                                authViewModel = authViewModel,
                                onNavigateToLogin = { isLogin = true },

                            )
                        }
                    }

                    // 3) جوه الـ Home / AddTask
                    else -> {
                        if (showAddTask) {
                            AddTaskScreen(
                                taskViewModel = taskViewModel,
                                onBack = { showAddTask = false }
                            )
                        } else {
                            HomeScreen(
                                userName = authViewModel.currentUserName() ?: "User",
                                taskViewModel = taskViewModel,
                                onAddTaskClick = { showAddTask = true },
                                onTaskClick = { },
                                onPomodoroClick = {  },
                                onTasksClick = {  },
                                onProfileClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
