
package com.example.todo_mindora_app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTitle
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthCardLogin
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthBackground
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTextField
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthPrimaryButton
import com.example.todo_mindora_app.ui.viewmodel.AuthUiState
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel
import com.example.todo_mindora_app.ui.theme.*

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit = {}
) {
    val state: AuthUiState = authViewModel.uiState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }

    AuthBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AuthTitle(
                title = "Welcome Back",
                subtitle = "Glad to see you again!"
            )

            Spacer(Modifier.height(16.dp))

            AuthCardLogin {

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
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 20.sp,
                        fontFamily = DescriptionFont
                    )
                }

                AuthPrimaryButton(
                    text = if (state.isLoading) "Logging in..." else "Login",
                    onClick = {
                        authViewModel.login(email.trim(), password.trim())
                              },
                    enabled = !state.isLoading
                )
            }

            Spacer(Modifier.height(100.dp))

            Row {
                Text(
                    text = "Don't have an account? ",
                    color = Cream,
                    fontFamily = DescriptionFont,
                    fontSize = 22.sp
                )

                Text(
                    text = "SIGN UP",
                    color = Cream,
                    modifier = Modifier.clickable { onNavigateToSignup() },
                    fontFamily = DescriptionFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
