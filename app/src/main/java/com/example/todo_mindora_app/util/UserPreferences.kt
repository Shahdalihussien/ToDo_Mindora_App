package com.example.todo_mindora_app.util

import android.content.Context

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("mindora_prefs", Context.MODE_PRIVATE)

    fun saveOnboardingCompleted() {
        sharedPreferences.edit().putBoolean("is_onboarding_completed", true).apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("is_onboarding_completed", false)
    }

    fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }
}