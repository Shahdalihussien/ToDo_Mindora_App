package com.example.todo_mindora_app.ui.navigation

import MainBackground
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.todo_mindora_app.ui.screens.calendar.CalendarScreen
import com.example.todo_mindora_app.ui.screens.home.HomeScreen
import com.example.todo_mindora_app.ui.screens.pomodoro.PomodoroScreen
//import com.example.todo_mindora_app.ui.screens.profile.ProfileScreen
import com.example.todo_mindora_app.ui.screens.task.AddTaskScreen
import com.example.todo_mindora_app.ui.screens.task.EditTaskScreen
import com.example.todo_mindora_app.ui.screens.task.TaskDetailsScreen

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

                /* ---------------- HOME ---------------- */

                composable("home") {
                    HomeScreen(
                        onOpenTask = { task ->
                            navController.navigate("task_details/${task.id}")
                        },
                        onEditTask = { task ->
                            navController.navigate("edit_task/${task.id}")
                        }
                    )
                }

                /* ---------------- ADD ---------------- */

                composable("add_task") {
                    AddTaskScreen(navController = navController)
                }

                /* ---------------- TASK DETAILS ---------------- */

                composable(
                    route = "task_details/{taskId}",
                    arguments = listOf(
                        navArgument("taskId") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    TaskDetailsScreen(
                        navController = navController
                    )
                }

                /* ---------------- EDIT TASK ---------------- */

                composable(
                    route = "edit_task/{taskId}",
                    arguments = listOf(
                        navArgument("taskId") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    EditTaskScreen(
                        navController = navController
                    )
                }

                /* ---------------- OTHER SCREENS ---------------- */

                composable("calendar") {
                    CalendarScreen()
                }

                composable("pomodoro") {
                    PomodoroScreen(navController = navController)
                }

//                composable("profile") {
//                    ProfileScreen()
//                }
            }
        }
    }
}

/* ---------------- DUMMY ---------------- */

