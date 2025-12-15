
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete


@Composable
fun HomeScreen(
    viewModel: TaskViewModel = viewModel(),
    onOpenTask: (TaskEntity) -> Unit = {},
    onEditTask: (TaskEntity) -> Unit = {},
) {

    val selectedDate by viewModel.selectedDate.collectAsState()
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val daysInMonth by viewModel.daysInMonth.collectAsState()

    val activeTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    val listState = rememberLazyListState()
    val todayIndex = daysInMonth.indexOf(LocalDate.now())

    LaunchedEffect(daysInMonth) {
        if (todayIndex >= 0) {
            listState.scrollToItem(todayIndex)
        }
    }

    Column(modifier = Modifier.padding(20.dp)) {

        val formatter = DateTimeFormatter.ofPattern("MMMM, yyyy", Locale.ENGLISH)
        Text(
            text = selectedDate.format(formatter),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = TitleFont,
            color = TextColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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

        Text(
            "Today's Tasks",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = TitleFont)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(activeTasks) { task ->
                TimelineTaskItem(
                    task = task,
                    onCheckClick = { viewModel.toggleTaskCompletion(task) },
                    onOpen = { onOpenTask(task) },
                    onEdit = { onEditTask(task) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }

            if (completedTasks.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Completed",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = TitleFont
                    )
                }

                items(completedTasks) { task ->
                    TimelineTaskItem(
                        task = task,
                        onCheckClick = { viewModel.toggleTaskCompletion(task) },
                        onOpen = { onOpenTask(task) },
                        onEdit = { onEditTask(task) },
                        onDelete = { viewModel.deleteTask(task) }
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
                RoundedCornerShape(15.dp)
            )
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .width(40.dp)
    ) {
        Text(
            text = dayName,
            color = if (isSelected) Color.White else TextColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = DescriptionFont
        )
        Text(
            text = dayNum,
            color = if (isSelected) Color.White else TextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = TitleFont
        )
    }
}



@Composable
fun TimelineTaskItem(
    task: TaskEntity,
    onCheckClick: () -> Unit,
    onOpen: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val cardColor = Color(task.color)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .height(IntrinsicSize.Min)
    ) {

        /* -------- TIMELINE DOT -------- */

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(if (task.isCompleted) cardColor else Color.White)
                    .border(2.dp, cardColor, CircleShape)
                    .clickable { onCheckClick() },
                contentAlignment = Alignment.Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .padding(top = 4.dp)
            ) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.4f),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f)),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardColor.copy(alpha = 0.85f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "${task.startTime} - ${task.endTime}",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontFamily = DescriptionFont
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        task.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = DescriptionFont,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Priority: ${task.priority}",
                        fontSize = 17.sp,
                        fontFamily = DescriptionFont,
                        color = Color.White
                    )

                    Surface(
                        color = Color.White.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Text(
                            task.category,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


