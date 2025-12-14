package com.example.todo_mindora_app.ui.screens.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todo_mindora_app.ui.components.CustomTextField
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.Locale

data class SubTaskUi(
    val title: String,
    val isCompleted: Boolean = false
)

@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(getFormattedDate(calendar)) }
    var startTime by remember { mutableStateOf("10:00 AM") }
    var endTime by remember { mutableStateOf("05:00 PM") }
    var selectedPriority by remember { mutableStateOf("Medium") }
    var selectedCategory by remember { mutableStateOf("Work") }

    // 2. متغيرات للـ SubTasks
    var currentSubTask by remember { mutableStateOf("") }
    var subTasksList by remember { mutableStateOf(listOf<SubTaskUi>()) }

    val colors = listOf(
        Color(0xFF00586A), Color(0xFF97192E), Color(0xFF0086A1),
        Color(0xFF5B5B5B), Color(0xFF007760), Color(0xFFEA8475)
    )
    var selectedColor by remember { mutableStateOf(colors[0]) }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text("Add Task", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextColor)
        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(value = title, onValueChange = { title = it }, label = "Title")
        CustomTextField(value = description, onValueChange = { description = it }, label = "Description", height = 80)

        // --- 3. قسم الـ Sub Tasks الجديد ---
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sub Tasks", style = MaterialTheme.typography.bodyMedium, color = TextColor)
            // Progress بسيط يوضح عدد المهام المضافة
            if (subTasksList.isNotEmpty()) {
                Text(
                    text = "${subTasksList.size} items",
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryColor
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(
                    value = currentSubTask,
                    onValueChange = { currentSubTask = it },
                    label = "Add new step..."
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (currentSubTask.isNotBlank()) {
                        subTasksList = subTasksList + SubTaskUi(title = currentSubTask)
                        currentSubTask = ""
                    }
                },
                modifier = Modifier
                    .background(DarkTeal, RoundedCornerShape(12.dp))
                    .size(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Subtask", tint = Color.White)
            }
        }

        // عرض قائمة المهام الفرعية المضافة
        Column(modifier = Modifier.padding(top = 12.dp)) {
            subTasksList.forEachIndexed { index, subTask ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // نقطة صغيرة كأنها Bullet
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(selectedColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(subTask.title, fontSize = 14.sp, color = TextColor)
                    }
                    // زر حذف للمهمة الفرعية لو كتبها غلط
                    IconButton(
                        onClick = {
                            subTasksList = subTasksList.toMutableList().apply { removeAt(index) }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableTextField(
            value = dueDate,
            label = "Due Date",
            icon = Icons.Default.DateRange,
            onClick = { showDatePicker(context, calendar) { date -> dueDate = date } }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                ClickableTextField(
                    value = startTime,
                    label = "Start Time",
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = { showTimePicker(context, calendar) { time -> startTime = time } }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                ClickableTextField(
                    value = endTime,
                    label = "End Time",
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = { showTimePicker(context, calendar) { time -> endTime = time } }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Priority", style = MaterialTheme.typography.bodyMedium, color = TextColor)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            val priorities = listOf("Low", "Medium", "High")
            priorities.forEach { priority ->
                val isSelected = priority == selectedPriority
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedPriority = priority },
                    label = { Text(priority, color = if(isSelected) Color.White else TextColor) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DarkTeal,
                        containerColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSelected, borderColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Category", style = MaterialTheme.typography.bodyMedium, color = TextColor)
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = selectedCategory,
            onValueChange = { selectedCategory = it },
            label = "Category Name"
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Task Color", style = MaterialTheme.typography.bodyMedium, color = TextColor)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEach { color ->
                val isSelected = color == selectedColor
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (isSelected) 3.dp else 0.dp,
                            color = if (isSelected) TextColor.copy(alpha = 0.6f) else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { selectedColor = color },
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(2f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (title.isNotEmpty()) {
                    viewModel.addTask(
                        title = title,
                        desc = description,
                        date = dueDate,
                        startTime = startTime,
                        endTime = endTime,
                        priority = selectedPriority,
                        category = selectedCategory,
                        color = selectedColor
                        // subTasks = subTasksList
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = DarkTeal),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Create a Task", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun ClickableTextField(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable { onClick() }) {
        CustomTextField(
            value = value,
            onValueChange = {},
            label = label,
            readOnly = true,
            trailingIcon = {
                Icon(icon, contentDescription = null, tint = Color.Gray)
            }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = onClick)
        )
    }
}
fun showDatePicker(context: Context, calendar: Calendar, onDateSelected: (String) -> Unit) {
    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val formattedDate = "$year-${String.format("%02d", month + 1)}-${String.format("%02d", day)}"
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun showTimePicker(context: Context, calendar: Calendar, onTimeSelected: (String) -> Unit) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val amPm = if (hourOfDay < 12) "AM" else "PM"
            val hour = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
            val formattedTime = String.format(Locale.US, "%02d:%02d %s", hour, minute, amPm)
            onTimeSelected(formattedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}

fun getFormattedDate(calendar: Calendar): String {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$year-${String.format("%02d", month)}-${String.format("%02d", day)}"
}