package com.example.todo_mindora_app.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val taskGroups = listOf("Work", "Personal", "Study", "Health", "Other")
    var selectedGroup by remember { mutableStateOf(taskGroups.first()) }
    var groupMenuExpanded by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf("Select start date") }
    var endDate by remember { mutableStateOf("Select end date") }

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                BackgroundColor,
                                BackgroundColor.copy(alpha = 0.0f)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextColor
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "Add Task",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = TextColor,
                        modifier = Modifier.weight(1f),
                    )

                    IconButton(
                        onClick = { /* notifications later */ },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = SecondaryColor
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BackgroundColor,
                            Color.White
                        )
                    )
                )
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                Spacer(Modifier.height(8.dp))

                if (showError && title.isBlank()) {
                    Text(
                        text = "Task title is required.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ErrorColor
                    )
                }

                // ░░ Task Group Card ░░
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Task Group",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextColor.copy(alpha = 0.6f)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(BackgroundColor)
                                .clickable { groupMenuExpanded = true }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            // small colored icon
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(PrimaryColor, SecondaryColor)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = selectedGroup.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.width(12.dp))

                            Text(
                                text = selectedGroup,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextColor,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextColor.copy(alpha = 0.7f)
                            )
                        }

                        DropdownMenu(
                            expanded = groupMenuExpanded,
                            onDismissRequest = { groupMenuExpanded = false }
                        ) {
                            taskGroups.forEach { group ->
                                DropdownMenuItem(
                                    text = { Text(group) },
                                    onClick = {
                                        selectedGroup = group
                                        groupMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Task Title",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextColor.copy(alpha = 0.6f)
                        )
                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                                if (it.isNotBlank()) showError = false
                            },
                            placeholder = { Text("e.g. Grocery shopping app design") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextColor.copy(alpha = 0.6f)
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text("Write a short description for this task") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 80.dp),
                            maxLines = 4
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DateCard(
                        label = "Start Date",
                        value = startDate,
                        onClick = { /* TODO: open date picker later */ }
                    )
                    DateCard(
                        label = "Due Date",
                        value = endDate,
                        onClick = { /* TODO: open date picker later */ }
                    )
                }

                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (title.isBlank()) {
                                showError = true
                            } else {
                                taskViewModel.addTask(
                                    title = title.trim(),
                                    description = description.ifBlank { null },
                                    project = selectedGroup
                                )
                                onBack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(8.dp, RoundedCornerShape(24.dp), clip = false),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Create Task",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DateCard(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextColor.copy(alpha = 0.6f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = SecondaryColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextColor
                )
            }
        }
    }
}
