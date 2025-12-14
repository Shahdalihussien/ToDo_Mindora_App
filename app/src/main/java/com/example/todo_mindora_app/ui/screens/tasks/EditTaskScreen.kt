package com.example.todo_mindora_app.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.ui.theme.*
import androidx.compose.foundation.clickable
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val context = LocalContext.current
    val taskId = navController.currentBackStackEntry?.arguments?.getInt("taskId")

    var task by remember { mutableStateOf<TaskEntity?>(null) }

    LaunchedEffect(taskId) {
        taskId?.let { task = viewModel.getTaskById(it) }
    }

    task?.let { existingTask ->
        var title by remember { mutableStateOf(existingTask.title) }
        var description by remember { mutableStateOf(existingTask.description) }
        var startTime by remember { mutableStateOf(existingTask.startTime) }
        var endTime by remember { mutableStateOf(existingTask.endTime) }
        var priority by remember { mutableStateOf(existingTask.priority) }
        var category by remember { mutableStateOf(existingTask.category) }
        var dueDate by remember { mutableStateOf(existingTask.date) }

        val taskColor = Color(existingTask.color)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(taskColor.copy(alpha = 0.25f))
                )
         {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Edit Task") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
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
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    FieldCard(label = "Title", value = title) { title = it }
                    FieldCard(label = "Description", value = description) { description = it }
                    ClickableFieldCard(
                        label = "Due Date",
                        value = dueDate
                    ) {
                        val calendar = Calendar.getInstance()
                        showDatePicker(
                            context = context,
                            calendar = calendar
                        ) { selectedDate ->
                            dueDate = selectedDate
                        }
                    }
                    FieldCard(label = "Start Time", value = startTime) { startTime = it }
                    FieldCard(label = "End Time", value = endTime) { endTime = it }
                    FieldCard(label = "Priority", value = priority) { priority = it }
                    FieldCard(label = "Category", value = category) { category = it }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.updateTask(
                                existingTask.copy(
                                    title = title,
                                    description = description,
                                    date = dueDate,
                                    startTime = startTime,
                                    endTime = endTime,
                                    priority = priority,
                                    category = category
                                )
                            )
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepRed)
                    ) {
                        Text("Save Changes", color = Color.White,fontFamily = DescriptionFont,
                            fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun FieldCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit

) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey.copy(alpha = 0.60f)
        ),
        elevation = CardDefaults.cardElevation(0.dp) ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black,

            )

            Spacer(modifier = Modifier.height(6.dp))

            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryColor
                )
            )
        }
    }
}

@Composable
fun ClickableFieldCard(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey.copy(alpha = 0.60f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = label,
                //style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                //style = MaterialTheme.typography.bodyMedium,
                color = TextColor,
                fontFamily = DescriptionFont,
                fontWeight = FontWeight.Bold

            )
        }
    }
}
