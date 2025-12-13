package com.example.todo_mindora_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.rememberNavController
import com.example.todo_mindora_app.ui.screens.splash.SplashScreen
import com.example.todo_mindora_app.ui.screens.onboarding.OnboardingScreen
import com.example.todo_mindora_app.ui.screens.auth.LoginScreen
import com.example.todo_mindora_app.ui.screens.auth.SignupScreen
import com.example.todo_mindora_app.ui.theme.ToDo_Mindora_AppTheme
import com.example.todo_mindora_app.ui.navigation.AppNavGraph
import com.example.todo_mindora_app.util.UserPreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDo_Mindora_AppTheme {
                val context = LocalContext.current
                val userPrefs = remember { UserPreferences(context) }
                val navController = rememberNavController()
                val logoFont = FontFamily(Font(R.font.museomoderno_light))

                var step by remember { mutableStateOf(0) }

                when (step) {
                    0 -> SplashScreen(
                        logoFont = logoFont,
                        onFinish = {
                            if (userPrefs.isLoggedIn()) {
                                step = 4
                            } else if (userPrefs.isOnboardingCompleted()) {
                                step = 2
                            } else {
                                step = 1
                            }
                        }
                    )

                    1 -> OnboardingScreen(
                        logoFont = logoFont,
                        onFinish = {
                            userPrefs.saveOnboardingCompleted()
                            step = 2
                        },
                        onLogin = {
                            userPrefs.saveOnboardingCompleted()
                            step = 2
                        },
                        onSignup = {
                            userPrefs.saveOnboardingCompleted()
                            step = 3
                        }
                    )

                    2 -> LoginScreen(
                        authViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                        onNavigateToSignup = { step = 3 },
                        onLoginSuccess = {
                            userPrefs.saveLoginState(true)
                            step = 4
                        }
                    )

                    3 -> SignupScreen(
                        authViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                        onNavigateToLogin = { step = 2 },
                        onSignupSuccess = {
                            userPrefs.saveLoginState(true)
                            step = 4
                        }
                    )

                    4 -> AppNavGraph(navController = navController)
                }
            }
        }
    }
}