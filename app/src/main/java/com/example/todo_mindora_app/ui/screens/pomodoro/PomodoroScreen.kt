package com.example.todo_mindora_app.ui.screens.pomodoro

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController // Import جديد
import com.example.todo_mindora_app.ui.theme.*
import com.example.todo_mindora_app.ui.viewmodel.PomodoroViewModel
import java.util.Locale

@Composable
fun PomodoroScreen(
    viewModel: PomodoroViewModel = viewModel(),
    navController: NavController
) {

    val activeColor by animateColorAsState(
        targetValue = if (viewModel.isFocusMode) SoftCoral else WarmBeige,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            color = Color.White.copy(alpha = 0.2f),
            shape = RoundedCornerShape(50),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(activeColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (viewModel.isFocusMode) "Focus Session" else "Relax Break",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkTeal,
                    fontFamily = TitleFont
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(260.dp)) {
                drawCircle(color = Color.White.copy(alpha = 0.1f), radius = size.minDimension / 2)
            }
            CleanCircularProgressBar(
                progress = viewModel.progress,
                mainColor = activeColor,
                trackColor = Color.White.copy(alpha = 0.25f),
                size = 280.dp,
                strokeWidth = 22.dp
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatTime(viewModel.currentTime),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = if (viewModel.isTimerRunning) "Stay Focused" else "Ready?",
                    fontSize = 18.sp,
                    color = TextColor.copy(alpha = 0.6f),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = DescriptionFont
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White.copy(alpha = 0.3f),
            shadowElevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.resetTimer() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = TextColor.copy(alpha = 0.6f))
                }

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(10.dp, CircleShape, spotColor = activeColor)
                        .clip(CircleShape)
                        .background(activeColor)
                        .clickable { if (viewModel.isTimerRunning) viewModel.pauseTimer() else viewModel.startTimer() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (viewModel.isTimerRunning) Icons.Rounded.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }

                IconButton(onClick = { viewModel.switchMode() }) {
                    Icon(imageVector = Icons.Rounded.SkipNext,
                        contentDescription = "Skip",
                        tint = TextColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(30.dp) )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun CleanCircularProgressBar(progress: Float, mainColor: Color, trackColor: Color, size: Dp, strokeWidth: Dp) {
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(durationMillis = 800))
    Canvas(modifier = Modifier.size(size)) {
        drawCircle(color = trackColor, style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round))
        drawArc(color = mainColor, startAngle = -90f, sweepAngle = 360 * animatedProgress, useCenter = false, style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round), size = Size(size.toPx(), size.toPx()), topLeft = Offset(0f, 0f))
    }
}

fun formatTime(millis: Long): String {
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format(Locale.US, "%02d:%02d", minutes, seconds)
}