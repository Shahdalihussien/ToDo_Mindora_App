package com.example.todo_mindora_app.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val taskId = navController.currentBackStackEntry?.arguments?.getInt("taskId")

    var task by remember { mutableStateOf<TaskEntity?>(null) }

    LaunchedEffect(taskId) {
        taskId?.let { task = viewModel.getTaskById(it) }
    }

    task?.let { taskData ->

        val taskColor = Color(taskData.color)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(taskColor.copy(alpha = 0.25f))
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Task Details") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    navController.navigate("edit_task/${taskData.id}")
                                }
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }

                            IconButton(
                                onClick = {
                                    viewModel.deleteTask(taskData)
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    DetailCard("Title", taskData.title)
                    DetailCard("Description", taskData.description)
                    DetailCard("Due Date", taskData.date)
                    DetailCard("Time", "${taskData.startTime} - ${taskData.endTime}")
                    DetailCard("Priority", taskData.priority)
                    DetailCard("Category", taskData.category)

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

/* ---------------- SMALL CARD ---------------- */

@Composable
fun DetailCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cream.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = TextColor,

            )
        }
    }
}
