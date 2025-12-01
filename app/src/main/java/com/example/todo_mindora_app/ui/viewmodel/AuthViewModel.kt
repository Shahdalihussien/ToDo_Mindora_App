package com.example.todo_mindora_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)

class AuthViewModel(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    var uiState by mutableStateOf(
        AuthUiState(
            isLoggedIn = firebaseAuth.currentUser != null
        )
    )
        private set

    fun signup(email: String, password: String) {
        if (email.isBlank() || password.length < 6) {
            uiState = uiState.copy(
                errorMessage = "Email must not be empty & password ≥ 6 chars"
            )
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                uiState = if (task.isSuccessful) {
                    AuthUiState(isLoggedIn = true)
                } else {
                    AuthUiState(
                        isLoggedIn = false,
                        errorMessage = task.exception?.message ?: "Signup failed"
                    )
                }
            }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Please enter email & password"
            )
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                uiState = if (task.isSuccessful) {
                    AuthUiState(isLoggedIn = true)
                } else {
                    AuthUiState(
                        isLoggedIn = false,
                        errorMessage = task.exception?.message ?: "Login failed"
                    )
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        uiState = AuthUiState(isLoggedIn = false)
    }

    fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
    fun currentUserName(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }

}
