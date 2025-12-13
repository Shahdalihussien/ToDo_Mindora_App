package com.example.todo_mindora_app.ui.screens.calendar

import MainBackground
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel = viewModel()
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val today = LocalDate.now()

    val allTasks by viewModel.allTasks.collectAsState(initial = emptyList())
    val tasksForSelectedDate = allTasks.filter { task ->
        task.date == selectedDate.toString()
    }

    val daysList = remember {
        val days = mutableListOf<LocalDate>()
        val start = today.minusDays(5)
        val end = today.plusDays(30)
        var current = start
        while (!current.isAfter(end)) {
            days.add(current)
            current = current.plusDays(1)
        }
        days
    }

    MainBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {

            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("MMMM, yyyy", Locale.ENGLISH)),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(daysList) { date ->
                    DateCard(
                        date = date,
                        isSelected = date == selectedDate,
                        onDateClick = { selectedDate = date }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (selectedDate == today) "Today's Tasks" else "Tasks for ${selectedDate.dayOfMonth} ${selectedDate.month}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (tasksForSelectedDate.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks yet! ",
                        fontSize = 18.sp,
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp) // مسافة عشان الناف بار
                ) {
                    items(tasksForSelectedDate) { task ->
                        CalendarTaskItem(task)
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateCard(
    date: LocalDate,
    isSelected: Boolean,
    onDateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(80.dp)
            .clickable { onDateClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(),
                fontSize = 12.sp,
                color = if (isSelected) DarkTeal else TextColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 20.sp,
                color = if (isSelected) DarkTeal else TextColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CalendarTaskItem(task: TaskEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(task.color))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Text(
                    text = "${task.startTime} - ${task.endTime}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}