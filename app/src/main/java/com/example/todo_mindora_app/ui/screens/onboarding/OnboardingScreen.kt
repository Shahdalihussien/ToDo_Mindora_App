package com.example.todo_mindora_app.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.todo_mindora_app.R
import com.example.todo_mindora_app.ui.theme.*

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope



@Composable
fun OnboardingScreen(
    logoFont: FontFamily,
    onFinish: () -> Unit,
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    val pages = listOf("page1", "page2", "page3", "page4")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })

    val gradient = Brush.verticalGradient(
        colors = listOf(
            DeepOrange,
            TealGreen,
            DarkTeal
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {

        SimpleBubbles()

        HorizontalPager(state = pagerState) { page ->
            when (page) {

                0 -> OnboardingPage(
                    title = "Organize your tasks",
                    desc = "Create,sort,and manage your to-do list easily",
                    imageRes = R.drawable.task,
                    showArrow = true,
                    onArrowClick = { scope ->
                        scope.launch { pagerState.animateScrollToPage(1) }
                    }
                )

                1 -> OnboardingPage(
                    title = "Focus with Pomodoro",
                    desc = "Use the Pomodoro timer to stay productive and avoid distractions",
                    imageRes = R.drawable.focus,
                    showArrow = true,
                    onArrowClick = { scope ->
                        scope.launch { pagerState.animateScrollToPage(2) }
                    }
                )

                2 -> OnboardingPage(
                    title = "Track your progress",
                    desc = "Stay motivated as you complete tasks and improve your workflow",
                    imageRes = R.drawable.productivity,
                    showArrow = true,
                    onArrowClick = { scope ->
                        scope.launch { pagerState.animateScrollToPage(3) }
                    }
                )

                3 -> FinalOnboardingPage(
                    logoFont = logoFont,
                    onLogin = onLogin,
                    onSignup = onSignup
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected) Color.White else Color.White.copy(0.4f)
                        )
                )
            }
        }
    }
}



// ---------------------------------------------------------------
// BACKGROUND BLURRED BUBBLES
// ---------------------------------------------------------------
@Composable
fun SimpleBubbles() {
    Box(modifier = Modifier.fillMaxSize()) {

        val bubbles = listOf(
            Triple(300, 5, 40),
            Triple(40, 50, 20),
            Triple(250, 80, 20),
            Triple(55, 110, 45),
            Triple(180, 150, 35),
            Triple(320, 250, 25),
            Triple(250, 300, 50),
            Triple(35, 350, 30),
            Triple(200, 370, 50),
            Triple(100, 400, 20),
            Triple(280, 550, 40),
            Triple(40, 575, 25),
            Triple(130, 600, 60),
            Triple(280, 655, 20),
            Triple(200, 700, 30),
            Triple(40, 720, 40),
            Triple(310, 750, 60)
        )

        bubbles.forEach {
            Box(
                modifier = Modifier
                    .offset(x = it.first.dp, y = it.second.dp)
                    .size(it.third.dp)
                    .clip(CircleShape)
                    .background(Cream.copy(alpha = 0.15f))
                    .blur(25.dp)
            )
        }
    }
}



@Composable
fun OnboardingPage(
    title: String,
    desc: String,
    imageRes: Int,
    showArrow: Boolean,
    onArrowClick: (CoroutineScope) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaleAnim = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.size(260.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .size((360 * scaleAnim.value).dp)
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = title,
            color = Cream,
            fontSize = 36.sp,
            fontFamily = TitleFont
        )


        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = desc,
            color = Cream.copy(0.9f),
            fontSize = 22.sp,
            fontFamily = DescriptionFont

        )

        Spacer(modifier = Modifier.height(150.dp))

        if (showArrow) {
            IconButton(
                onClick = { onArrowClick(scope) },
                modifier = Modifier
                    .align(Alignment.End)
                    .size(70.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}




@Composable
fun FinalOnboardingPage(
    logoFont: FontFamily,
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    val scaleAnim = remember { Animatable(0.6f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1.15f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(900)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.mindora_logo),
            contentDescription = "",
            modifier = Modifier
                .size((250 * scaleAnim.value).dp)
                .alpha(alphaAnim.value)
        )



        Text(
            text = "Welcome to",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = DescriptionFont,
            modifier = Modifier.alpha(alphaAnim.value)
        )

        Text(
            text = "MINDORA",
            fontFamily = logoFont,
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.alpha(alphaAnim.value)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(65.dp)
                .alpha(alphaAnim.value),
            colors = ButtonDefaults.buttonColors(containerColor = DeepRed.copy(alpha = 0.85f))
        ) {
            Text(
                "Login",
                color = Color.White,
                fontSize = 25.sp,
                fontFamily = DescriptionFont,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSignup,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(65.dp)
                .alpha(alphaAnim.value),
            colors = ButtonDefaults.buttonColors(containerColor = DeepRedLight.copy(alpha = 0.85f))
        ) {
            Text(
                "Signup",
                color = Color.White,
                fontSize = 25.sp,
                fontFamily = DescriptionFont,
                fontWeight = FontWeight.SemiBold
            )
        }


    }
}
