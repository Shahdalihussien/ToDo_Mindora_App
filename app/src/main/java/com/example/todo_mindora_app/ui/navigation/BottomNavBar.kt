package com.example.todo_mindora_app.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo_mindora_app.R

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val barColor = Color(0xFFEAD6D0)
    val deepRed = Color(0xFF8B1E1E)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(65.dp)
                .shadow(
                    elevation = 14.dp,
                    shape = RoundedCornerShape(24.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = barColor)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CustomNavItem(
                    iconRes = R.drawable.home,
                    isSelected = currentRoute == "home",
                    deepRed = deepRed,
                    barColor = barColor
                ) {
                    navController.navigate("home")
                }

                CustomNavItem(
                    iconRes = R.drawable.pomodoro,
                    isSelected = currentRoute == "pomodoro",
                    deepRed = deepRed,
                    barColor = barColor
                ) {
                    navController.navigate("pomodoro")
                }

                CustomNavItem(
                    iconRes = R.drawable.add, // icon بتاع Add
                    isSelected = currentRoute == "add_task",
                    deepRed = deepRed,
                    barColor = barColor
                ) {
                    navController.navigate("add_task")
                }

                CustomNavItem(
                    iconRes = R.drawable.calendar,
                    isSelected = currentRoute == "calendar",
                    deepRed = deepRed,
                    barColor = barColor
                ) {
                    navController.navigate("calendar")
                }
            }
        }
    }
}

@Composable
fun CustomNavItem(
    iconRes: Int,
    isSelected: Boolean,
    deepRed: Color,
    barColor: Color,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) deepRed else Color.Transparent
    val iconColor = if (isSelected) barColor else deepRed

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(30.dp)
        )
    }
}
