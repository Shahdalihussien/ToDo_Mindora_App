package com.example.todo_mindora_app.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_mindora_app.R
import com.example.todo_mindora_app.ui.theme.*
import kotlinx.coroutines.delay



@Composable
fun SplashScreen(
    logoFont: FontFamily,
    onFinish: () -> Unit
) {

    val scaleAnim = remember { Animatable(0f) }

    LaunchedEffect(true) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000)
        )
        delay(5000)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        DarkTeal,
                        TealGreen,
                        SoftCoral,
                        DeepRed
                    )
                )
            )
    ) {

        // 🌟 RADIAL ثابت زي ما هو
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            DarkTeal.copy(alpha = 0.80f),
                            PastelTeal.copy(alpha = 0.55f),
                            TealGreen.copy(alpha = 0.35f),
                            PastelTeal.copy(alpha = 0.25f),
                            DeepRed.copy(alpha = 0.18f),
                            SoftCoral.copy(alpha = 0.10f),
                            Color.Transparent
                        ),
                        center = Offset(540f, 900f),
                        radius = 1100f
                    )
                )
        )

        // 🌟 LOGO + TEXT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp),   // ← شيلت الأوفست الأعلى بالكامل
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.mindora_logo),
                contentDescription = "Mindora Logo",
                modifier = Modifier
                    .offset(y = (-40).dp)   // ← هنا بقى السحر: رفعته فوق الدايرة *بالظبط*
                    .size((360 * scaleAnim.value).dp)
            )

            Spacer(modifier = Modifier.height(5.dp))  // مسافة بسيطة وشيك

            Text(
                text = "MINDORA",
                fontFamily = logoFont,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 55.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .offset(y = (-80).dp)  // ← ينزل تحت اللوجو بس لسه فوق الطبيعي
            )
        }
    }
}

