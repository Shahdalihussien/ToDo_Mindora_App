package com.example.todo_mindora_app.ui.screens.calendar

import MainBackground
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel = viewModel()
) {
    var currentMonth by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val allTasks by viewModel.allTasks.collectAsState(initial = emptyList())

    val tasksForSelectedDate = allTasks.filter {
        it.date == selectedDate.toString()
    }

    // 👇 كل الأيام اللي عليها Tasks
    val taskDates = allTasks.map { it.date }.toSet()

    MainBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            MonthHeader(
                month = currentMonth,
                onPrev = { currentMonth = currentMonth.minusMonths(1) },
                onNext = { currentMonth = currentMonth.plusMonths(1) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DaysOfWeekRow()

            MonthCalendar(
                month = currentMonth,
                selectedDate = selectedDate,
                taskDates = taskDates,
                onDateSelected = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tasks for ${selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM"))}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (tasksForSelectedDate.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks yet!",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(tasksForSelectedDate) { task ->
                        CalendarTaskItem(task)
                    }
                }
            }
        }
    }
}

/* -------------------- MONTH HEADER -------------------- */

@Composable
fun MonthHeader(
    month: LocalDate,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrev) { Text("<", fontSize = 22.sp) }

        Text(
            text = month.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        IconButton(onClick = onNext) { Text(">", fontSize = 22.sp) }
    }
}

/* -------------------- DAYS OF WEEK -------------------- */

@Composable
fun DaysOfWeekRow() {
    val days = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    Row(modifier = Modifier.fillMaxWidth()) {
        days.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

/* -------------------- CALENDAR GRID -------------------- */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthCalendar(
    month: LocalDate,
    selectedDate: LocalDate,
    taskDates: Set<String>,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = month.lengthOfMonth()
    val firstDayIndex = month.dayOfWeek.value % 7

    val days = mutableListOf<LocalDate?>()
    repeat(firstDayIndex) { days.add(null) }
    for (day in 1..daysInMonth) {
        days.add(month.withDayOfMonth(day))
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(days) { date ->
            if (date == null) {
                Spacer(modifier = Modifier.size(48.dp))
            } else {
                val isSelected = date == selectedDate
                val hasTasks = taskDates.contains(date.toString())

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when {
                                isSelected -> DarkTeal
                                hasTasks -> DarkTeal.copy(alpha = 0.25f)
                                else -> Color.White.copy(alpha = 0.35f)
                            }
                        )
                        .clickable { onDateSelected(date) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextColor
                        )

                        if (hasTasks && !isSelected) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(DarkTeal)
                            )
                        }
                    }
                }
            }
        }
    }
}

/* -------------------- TASK ITEM -------------------- */

@Composable
fun CalendarTaskItem(task: TaskEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
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
