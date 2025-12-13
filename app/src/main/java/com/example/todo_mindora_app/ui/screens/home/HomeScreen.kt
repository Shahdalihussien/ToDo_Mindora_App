package com.example.todo_mindora_app.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {

    val selectedDate by viewModel.selectedDate.collectAsState()
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val daysInMonth by viewModel.daysInMonth.collectAsState()

    Column(modifier = Modifier.padding(20.dp)) {
        val formatter = DateTimeFormatter.ofPattern("MMMM, yyyy", Locale.ENGLISH)
        Text(
            text = selectedDate.format(formatter),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(daysInMonth) { date ->
                DayChip(
                    dateObj = date,
                    isSelected = date == selectedDate,
                    onClick = { viewModel.onDateSelected(date) }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text("Today's Tasks", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = TextColor)
        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                Text("No tasks yet!", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks) { task ->
                    TimelineTaskItem(
                        task = task,
                        onCheckClick = { viewModel.toggleTaskCompletion(task) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}


@Composable
fun DayChip(dateObj: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val dayName = dateObj.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)).uppercase()
    val dayNum = dateObj.dayOfMonth.toString()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (isSelected) DarkTeal else Color.White.copy(alpha = 0.5f),
                RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .width(40.dp)
    ) {
        Text(text = dayName, color = if (isSelected) Color.White else TextColor, fontSize = 12.sp)
        Text(text = dayNum, color = if (isSelected) Color.White else TextColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TimelineTaskItem(task: TaskEntity, onCheckClick: () -> Unit) {

    val cardColor = Color(task.color)

    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (task.isCompleted) TealGreen else Color.White)
                    .border(2.dp, if (task.isCompleted) TealGreen else cardColor, CircleShape)
                    .clickable { onCheckClick() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
            Canvas(modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .padding(vertical = 4.dp)) {
                drawLine(
                    color = Color.Gray.copy(alpha = 0.5f),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("${task.startTime} - ${task.endTime}", fontSize = 12.sp, color = TextColor.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(task.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Priority: ${task.priority}", fontSize = 12.sp, color = TextColor.copy(alpha = 0.6f))

                    Surface(color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp)) {
                        Text(task.category, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp)
                    }
                }
            }
        }
    }
}