package com.example.todo_mindora_app.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.todo_mindora_app.R
import com.example.todo_mindora_app.ui.screens.auth.AuthComponents

import com.example.todo_mindora_app.ui.theme.AccentColor
import com.example.todo_mindora_app.ui.theme.SecondaryColor
import com.example.todo_mindora_app.ui.theme.TextColor
import kotlinx.coroutines.launch



data class OnboardingPage(
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int,
    val bullets: List<String>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Organize your day ",
            subtitle = "Create and track your tasks in one calm space.",
            imageRes = R.drawable.manage,
            bullets = listOf(

            )
        ),
        OnboardingPage(
            title = "Stay focused ",
            subtitle = "Use Pomodoro sessions to stay in deep focus.",
            imageRes = R.drawable.foucs,
            bullets = listOf(

            )
        ),
        OnboardingPage(
            title = "See your progress ",
            subtitle = "Understand your productivity over time.",
            imageRes = R.drawable.dashboard,
            bullets = listOf(
            )
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    AuthComponents.AuthBackground {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            TextButton(
                onClick = onFinish,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 24.dp)
            ) {
                Text("Skip", color = SecondaryColor)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    val pageData = pages[page]
                    val isActive = pagerState.currentPage == page

                    OnboardingPageContent(
                        page = pageData,
                        isActive = isActive
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        IndicatorDot(isSelected = index == pagerState.currentPage)
                    }
                }

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AuthComponents.OnboardingNavButton(
                        icon = if (pagerState.currentPage == pages.lastIndex)
                            Icons.Default.Check
                        else
                            Icons.Default.ArrowForward,
                        onClick = {
                            if (pagerState.currentPage == pages.lastIndex) {
                                onFinish()
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        }
                    )
                }

            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    isActive: Boolean
) {
    val imageScale by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.9f,
        label = "imageScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .size(200.dp)
                .scale(imageScale),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 10.0f)
            ),
            elevation = CardDefaults.cardElevation(0.3.dp)
        ) {
             Image(
                 painter = painterResource(id = page.imageRes),
                 contentDescription = null,
                 modifier = Modifier.fillMaxSize(),
                 contentScale = ContentScale.Crop
             )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Image",
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryColor
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = page.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextColor,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            page.bullets.forEach {
                BulletRow(text = it)
            }
        }
    }
}

@Composable
private fun BulletRow(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .then(
                    Modifier
                        .background(AccentColor, shape = RoundedCornerShape(50))
                )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun IndicatorDot(isSelected: Boolean) {
    val width = if (isSelected) 22.dp else 10.dp
    val height = 8.dp

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(width)
            .height(height)
            .background(
                color = if (isSelected)
                    SecondaryColor
                else
                    AccentColor.copy(alpha = 0.4f),
                shape = RoundedCornerShape(50)
            )
    )
}
