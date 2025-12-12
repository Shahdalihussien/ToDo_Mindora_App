package com.example.todo_mindora_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.rememberNavController
import com.example.todo_mindora_app.ui.screens.splash.SplashScreen
import com.example.todo_mindora_app.ui.screens.onboarding.OnboardingScreen
import com.example.todo_mindora_app.ui.screens.auth.LoginScreen
import com.example.todo_mindora_app.ui.screens.auth.SignupScreen
import com.example.todo_mindora_app.ui.theme.ToDo_Mindora_AppTheme
//import com.example.todo_mindora_app.ui.navigation.AppNavGraph
import com.example.todo_mindora_app.R
import androidx.navigation.NavHostController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDo_Mindora_AppTheme {

                val logoFont = FontFamily(
                    Font(R.font.museomoderno_light)
                )

                var step by remember { mutableStateOf(0) }

                val navController = rememberNavController()

                when (step) {
                    0 -> SplashScreen(
                        logoFont = logoFont,
                        onFinish = { step = 1 }
                    )

                    1 -> OnboardingScreen(
                        logoFont = logoFont,
                        onFinish = { step = 2 },
                        onLogin = { step = 2 },
                        onSignup = { step = 3 }
                    )

                    2 -> LoginScreen(
                        authViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                        onNavigateToSignup = { step = 3 },
                        onLoginSuccess = { step = 4 }
                    )

                    3 -> SignupScreen(
                        authViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                        onNavigateToLogin = { step = 2 },
                        onSignupSuccess = { step = 4 }
                    )

                    //4 -> AppNavGraph(navController = navController)
                }
            }
        }
    }
}
