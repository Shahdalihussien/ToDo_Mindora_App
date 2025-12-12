//package com.example.todo_mindora_app.ui.screens.auth
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import com.example.todo_mindora_app.ui.theme.ErrorColor
//import com.example.todo_mindora_app.ui.viewmodel.AuthUiState
//import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel
//
//@Composable
//fun SignupScreen(
//    authViewModel: AuthViewModel,
//    onNavigateToLogin: () -> Unit
//) {
//    val state: AuthUiState = authViewModel.uiState
//
//    var fullName by remember { mutableStateOf("") }
//    var username by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var localError by remember { mutableStateOf<String?>(null) }
//
//    // ندمج AuthComponents هنا
//    AuthComponents.AuthBackground {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            AuthComponents.AuthTitle(
//                title = "Create Account",
//                subtitle = "Build your Mindora profile and get productive"
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            AuthComponents.AuthCard {
//
//                // Full name
//                AuthComponents.AuthTextField(
//                    value = fullName,
//                    onValueChange = { fullName = it },
//                    label = "Full name"
//                )
//
//                // Username
//                AuthComponents.AuthTextField(
//                    value = username,
//                    onValueChange = { username = it },
//                    label = "Username"
//                )
//
//                // Email
//                AuthComponents.AuthTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = "Email"
//                )
//
//                // Password
//                AuthComponents.AuthTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = "Password (min 6 chars)",
//                    visualTransformation = PasswordVisualTransformation()
//                )
//
//                // Confirm Password
//                AuthComponents.AuthTextField(
//                    value = confirmPassword,
//                    onValueChange = { confirmPassword = it },
//                    label = "Confirm password",
//                    visualTransformation = PasswordVisualTransformation()
//                )
//
//                val errorToShow = localError ?: state.errorMessage
//                if (!errorToShow.isNullOrEmpty()) {
//                    Text(
//                        text = errorToShow,
//                        color = ErrorColor,
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                }
//
//                Spacer(Modifier.height(6.dp))
//
//                AuthComponents.AuthPrimaryButton(
//                    text = if (state.isLoading) "Creating account..." else "Sign Up",
//                    onClick = {
//                        localError = null
//
//                        when {
//                            fullName.isBlank() || username.isBlank() -> {
//                                localError = "Please enter your full name and username"
//                            }
//                            email.isBlank() -> {
//                                localError = "Email is required"
//                            }
//                            password.length < 6 -> {
//                                localError = "Password must be at least 6 characters"
//                            }
//                            password != confirmPassword -> {
//                                localError = "Passwords do not match"
//                            }
//                            else -> {
//                                authViewModel.signup(email.trim(), password.trim())
//                            }
//                        }
//                    },
//                    enabled = !state.isLoading
//                )
//            }
//
//            Spacer(Modifier.height(12.dp))
//
//            Row {
//                Text(
//                    text = "Don't have an account? ",
//                    color = DeepBlue.copy(alpha = 0.85f),
//                    fontWeight = FontWeight.Bold
//                )
////                Text(
////                    text = "Login",
////                    color = MaterialTheme.colorScheme.primary,
////                    color = DeepBlue.copy(alpha = 0.85f),
////                    fontWeight = FontWeight.Bold,
////                    modifier = Modifier.clickable { onNavigateToLogin() }
////                )
//            }
//        }
//    }
//}

package com.example.todo_mindora_app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthBackground
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthCardSignup
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTitle
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthTextField
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents.AuthPrimaryButton
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.AuthUiState
import com.example.todo_mindora_app.ui.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    val state: AuthUiState = authViewModel.uiState

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    AuthBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AuthTitle(
                title = "Create Account",
                subtitle = "Build your Mindora profile"
            )

            Spacer(Modifier.height(48.dp))

            AuthCardSignup {

                AuthTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Full Name"
                )

                AuthTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Username"
                )

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

                AuthTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                    visualTransformation = PasswordVisualTransformation()
                )

                val errorToShow = localError ?: state.errorMessage
                if (!errorToShow.isNullOrEmpty()) {
                    Text(
                        text = errorToShow,
                        color = ErrorColor,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 20.sp,
                        fontFamily = DescriptionFont
                    )
                }

                AuthPrimaryButton(
                    text = if (state.isLoading) "Creating account..." else "Sign Up",
                    onClick = {
                        localError = null

                        when {
                            fullName.isBlank() || username.isBlank() -> {
                                localError = "Full name and username are required."
                            }
                            email.isBlank() -> {
                                localError = "Email is required."
                            }
                            password.length < 6 -> {
                                localError = "Password must be at least 6 characters."
                            }
                            password != confirmPassword -> {
                                localError = "Passwords don't match."
                            }
                            else -> {
                                authViewModel.signup(email.trim(), password.trim())
                            }
                        }
                    },
                    enabled = !state.isLoading
                )
            }

            Spacer(Modifier.height(70.dp))

            Row {
                Text(
                    text = "Already have an account? ",
                    color = Cream,
                    fontFamily = DescriptionFont,
                    fontSize = 22.sp
                )

                Text(
                    text = "LOGIN",
                    color = Cream,
                    fontFamily = DescriptionFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}


