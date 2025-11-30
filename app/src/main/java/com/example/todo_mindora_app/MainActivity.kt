package com.example.todo_mindora_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_mindora_app.ui.screens.auth.LoginScreen
import com.example.todo_mindora_app.ui.screens.auth.SignupScreen
import com.example.todo_mindora_app.ui.theme.ToDo_Mindora_AppTheme
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDo_Mindora_AppTheme {

                var isLogin by remember { mutableStateOf(true) }
                val authViewModel: AuthViewModel = viewModel()

                if (isLogin) {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onNavigateToSignup = {
                            isLogin = false
                        }
                    )
                } else {
                    SignupScreen(
                        authViewModel = authViewModel,
                        onNavigateToLogin = {
                            isLogin = true
                        }
                    )
                }
            }
        }
    }
}
