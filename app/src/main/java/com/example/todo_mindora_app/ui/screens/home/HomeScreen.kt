package com.example.todo_mindora_app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo_mindora_app.data.local.entity.Task
import com.example.todo_mindora_app.ui.theme.*

import com.example.todo_mindora_app.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.map

/* ---------- Tabs Enum ---------- */

enum class BottomTab {
    HOME, TASKS, FOCUS, PROFILE
}

/* ---------- UI Model لمجموعات التاسكات ---------- */

data class TaskGroupUi(
    val title: String,
    val tasksCount: Int,
    val completedCount: Int
) {
    val progress: Float
        get() = if (tasksCount == 0) 0f else completedCount.toFloat() / tasksCount.toFloat()
}

/* ---------- Screen ---------- */

@Composable
fun HomeScreen(
    userName: String = "User",
    taskViewModel: TaskViewModel,
    onAddTaskClick: () -> Unit = {},
    onTaskClick: (Long) -> Unit = {},
    onPomodoroClick: () -> Unit = {},
    onTasksClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // 🔹 داتا جاية من الـ ViewModel
    val inProgressTasks by taskViewModel.inProgressTasks.collectAsState()
    val allTasks by taskViewModel.allTasks.collectAsState()
    val todayProgress by taskViewModel.todayProgress.collectAsState()

    // 🔹 نحسب جروبات حسب الـ project
    val taskGroups: List<TaskGroupUi> = remember(allTasks) {
        allTasks
            .groupBy { it.project ?: "General" }
            .map { (project, tasks) ->
                val completed = tasks.count { it.isCompleted }
                TaskGroupUi(
                    title = project,
                    tasksCount = tasks.size,
                    completedCount = completed
                )
            }
    }

    var selectedTab by remember { mutableStateOf(BottomTab.HOME) }

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
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                HomeTopSection(userName = userName)
            },
            bottomBar = {
                ElegantBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                        when (tab) {
                            BottomTab.HOME -> { /* already here */ }
                            BottomTab.TASKS -> onTasksClick()
                            BottomTab.FOCUS -> onPomodoroClick()
                            BottomTab.PROFILE -> onProfileClick()
                        }
                    },
                    onCenterClick = onAddTaskClick
                )
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                // كارت اليوم، الـ progress هنا Dynamic
                item {
                    TodaySummaryCard(
                        progress = todayProgress,
                        onViewTaskClick = { /* TODO: navigate to today's tasks */ }
                    )
                }

                item {
                    Text(
                        text = "In Progress",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = TextColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                item {
                    if (inProgressTasks.isEmpty()) {
                        Text(
                            text = "No tasks in progress yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColor.copy(alpha = 0.6f)
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(inProgressTasks, key = { it.id }) { task ->
                                InProgressTaskCard(
                                    task = task,
                                    onClick = { onTaskClick(task.id) }
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Task Groups",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = TextColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                items(taskGroups, key = { it.title }) { group ->
                    TaskGroupRow(group = group)
                }

                item { Spacer(Modifier.height(80.dp)) } // مسافة فوق الـ bottom bar
            }
        }
    }
}

/* ---------- Top Section ---------- */

@Composable
private fun HomeTopSection(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar circle بالحروف الأولى
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(SecondaryColor.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                val initials = userName
                    .split(" ")
                    .mapNotNull { it.firstOrNull()?.uppercase() }
                    .joinToString("")
                    .ifEmpty { "U" }

                Text(
                    text = initials,
                    color = SecondaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hello 👋",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextColor.copy(alpha = 0.6f)
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = TextColor
                )
            }

            IconButton(onClick = { /* notifications */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = SecondaryColor
                )
            }
        }
    }
}

/* ---------- Cards ---------- */

@Composable
private fun TodaySummaryCard(
    progress: Float,
    onViewTaskClick: () -> Unit
) {
    val safeProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SecondaryColor,
                        PrimaryColor
                    )
                )
            )
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Your today's task\nalmost done!",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )

            Text(
                text = "Keep going, you’re doing great ✨",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.85f)
            )

            Button(
                onClick = onViewTaskClick,
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = SecondaryColor
                ),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp)
            ) {
                Text("View Task")
            }
        }

        Column(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = safeProgress,
                    modifier = Modifier.size(72.dp),
                    strokeWidth = 8.dp,
                    color = Color.White
                )
                Text(
                    text = "${(safeProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun InProgressTaskCard(
    task: Task,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(230.dp)
            .height(120.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(22.dp),
                clip = true
            )
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.project ?: "General",
                style = MaterialTheme.typography.labelSmall,
                color = TextColor.copy(alpha = 0.6f)
            )
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = TextColor,
                maxLines = 2
            )

            Spacer(Modifier.height(6.dp))

            // لسه معندناش progress per task، فهنفرض نص ونطوره بعدين
            val fakeProgress = 0.5f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(50))
                    .background(AccentColor.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fakeProgress)
                        .clip(RoundedCornerShape(50))
                        .background(SecondaryColor)
                )
            }
        }
    }
}

@Composable
private fun TaskGroupRow(group: TaskGroupUi) {
    val progress = group.progress.coerceIn(0f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(PrimaryColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = SecondaryColor
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = TextColor
            )
            Text(
                text = "${group.tasksCount} tasks • ${group.completedCount} done",
                style = MaterialTheme.typography.labelSmall,
                color = TextColor.copy(alpha = 0.6f)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = SecondaryColor
            )
            Spacer(Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .width(70.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                color = SecondaryColor,
                trackColor = PrimaryColor.copy(alpha = 0.1f)
            )
        }
    }
}

/* ---------- Elegant Bottom Bar ---------- */

@Composable
private fun ElegantBottomBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    onCenterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Glassy background
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(70.dp)
                .shadow(12.dp, RoundedCornerShape(30.dp), clip = true)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.97f),
                            Color.White.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // Icons row
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(70.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomIconItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedTab == BottomTab.HOME,
                onClick = { onTabSelected(BottomTab.HOME) }
            )

            BottomIconItem(
                icon = Icons.Default.List,
                label = "Tasks",
                isSelected = selectedTab == BottomTab.TASKS,
                onClick = { onTabSelected(BottomTab.TASKS) }
            )

            Spacer(Modifier.width(40.dp)) // مساحة لل FAB اللي في النص

            BottomIconItem(
                icon = Icons.Default.Info,
                label = "Focus",
                isSelected = selectedTab == BottomTab.FOCUS,
                onClick = { onTabSelected(BottomTab.FOCUS) }
            )

            BottomIconItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = selectedTab == BottomTab.PROFILE,
                onClick = { onTabSelected(BottomTab.PROFILE) }
            )
        }

        // Center FAB
        Box(
            modifier = Modifier
                .offset(y = (-26).dp)
                .size(60.dp)
                .shadow(12.dp, CircleShape, clip = true)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryColor, SecondaryColor)
                    ),
                    shape = CircleShape
                )
                .clickable { onCenterClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun BottomIconItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) PrimaryColor else TextColor.copy(alpha = 0.55f)
    val bg = if (isSelected) PrimaryColor.copy(alpha = 0.12f) else Color.Transparent

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = tint
        )
    }
}
