package com.example.todo_mindora_app.ui.navigation

import MainBackground
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo_mindora_app.ui.screens.home.HomeScreen
import com.example.todo_mindora_app.ui.screens.pomodoro.PomodoroScreen
import com.example.todo_mindora_app.ui.screens.task.AddTaskScreen
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo_mindora_app.ui.screens.calendar.CalendarScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MainBackground {
        Scaffold(
            containerColor = Color.Transparent,

            bottomBar = {
                if (currentRoute != "pomodoro") {
                    BottomNavBar(
                        navController = navController,
                        onAddClick = { navController.navigate("add_task") }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("add_task") { AddTaskScreen(navController = navController) }
                composable("calendar") { CalendarScreen() }

                composable("pomodoro") {
                    PomodoroScreen(navController = navController)
                }

                composable("profile") { DummyScreen("Profile") }
            }
        }
    }
}
@Composable
fun DummyScreen(name: String) {
    androidx.compose.foundation.layout.Box(
      //  modifier = androidx.compose.foundation.layout.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(text = name)
    }
}