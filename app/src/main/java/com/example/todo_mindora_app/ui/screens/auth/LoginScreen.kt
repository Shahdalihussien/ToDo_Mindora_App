package com.example.todo_mindora_app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthBackground
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthCard
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthPrimaryButton
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTextField
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTitle
import com.example.todo_mindora_app.ui.theme.ErrorColor
import com.example.todo_mindora_app.ui.viewmodel.AuthUiState
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignup: () -> Unit
) {
    val state: AuthUiState = authViewModel.uiState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AuthTitle(
                title = "Welcome back",
                subtitle = "Login to your Mindora space"
            )

            Spacer(Modifier.height(16.dp))

            AuthCard {
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email"
                )

                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    visualTransformation = PasswordVisualTransformation()
                )

                if (!state.errorMessage.isNullOrEmpty()) {
                    Text(
                        text = state.errorMessage,
                        color = ErrorColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(6.dp))

                AuthPrimaryButton(
                    text = if (state.isLoading) "Logging in..." else "Login",
                    onClick = {
                        authViewModel.login(email.trim(), password.trim())
                    },
                    enabled = !state.isLoading
                )
            }

            Spacer(Modifier.height(12.dp))

            Row {
                Text("Don't have an account? ")
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onNavigateToSignup() }
                )
            }
        }
    }
}
