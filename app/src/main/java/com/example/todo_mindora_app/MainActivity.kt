package com.example.todo_mindora_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_mindora_app.ui.screens.auth.LoginScreen
import com.example.todo_mindora_app.ui.screens.auth.SignupScreen
import com.example.todo_mindora_app.ui.screens.onboarding.OnboardingScreen
import com.example.todo_mindora_app.ui.theme.ToDo_Mindora_AppTheme
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("mindora_prefs", MODE_PRIVATE)

        setContent {
            ToDo_Mindora_AppTheme {

                var hasSeenOnboarding by remember {
                    mutableStateOf(prefs.getBoolean("has_seen_onboarding", false))
                }
                var isLogin by remember { mutableStateOf(true) }

                val authViewModel: AuthViewModel = viewModel()

                if (!hasSeenOnboarding) {
                    OnboardingScreen(
                        onFinish = {
                            prefs.edit()
                                .putBoolean("has_seen_onboarding", true)
                                .apply()
                            hasSeenOnboarding = true
                        }
                    )
                } else if (isLogin) {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onNavigateToSignup = { isLogin = false }
                    )
                } else {
                    SignupScreen(
                        authViewModel = authViewModel,
                        onNavigateToLogin = { isLogin = true }
                    )
                }
            }
        }
    }
}

