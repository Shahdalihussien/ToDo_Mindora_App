package com.example.todo_mindora_app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_mindora_app.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.todo_mindora_app.R
import com.example.todo_mindora_app.ui.theme.DescriptionFont


// -----------------------------------------------------
// AUTH COMPONENTS
// -----------------------------------------------------
object AuthComponents {

    // -----------------------------------------------------
    // 🌅 Improved Background (Top & Bottom color – center Cream)
    // -----------------------------------------------------
    @Composable
    fun AuthBackground(content: @Composable BoxScope.() -> Unit) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Optional dark overlay (to make text more readable)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.40f))
            )

            // The content (buttons, texts, fields)
            content()
        }
    }


//     -----------------------------------------------------
//     🧊 Glass Card (not transparent white – soft cream)
//     -----------------------------------------------------
    @Composable
    fun AuthCardLogin(content: @Composable ColumnScope.() -> Unit) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Cream.copy(alpha = 0.60f)
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 36.dp)  // 🔥 أفضل توازن
                    .heightIn(min = 280.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),   // 🔥 تباعد واضح
                content = content
            )
        }
    }
    @Composable
    fun AuthCardSignup(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Cream.copy(alpha = 0.60f)
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 20.dp),  // كان 26 → قللناه
                verticalArrangement = Arrangement.spacedBy(12.dp),   // كانت 20 → قللناها
                content = content
            )
        }
    }



    // -----------------------------------------------------
    // 🔠 Titles
    // -----------------------------------------------------
    @Composable
    fun AuthTitle(title: String, subtitle: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 50.sp,
                    color = Cream,
                    fontFamily = TitleFont
                )
            )

            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Cream.copy(alpha = 0.80f),
                    fontFamily = DescriptionFont

                )
            )
        }
    }

    // -----------------------------------------------------
    // ✏️ TextField
    // -----------------------------------------------------
    @Composable
    fun AuthTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, fontSize = 20.sp, fontFamily = DescriptionFont) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkTeal,
                unfocusedBorderColor = TealGreen,
                focusedLabelColor = DarkTeal,
                cursorColor = DarkTeal,
                focusedContainerColor = Cream.copy(alpha = 0.90f),
                unfocusedContainerColor = Cream.copy(alpha = 0.65f)
            )
        )
    }

    // -----------------------------------------------------
    // 🔘 Primary Button
    // -----------------------------------------------------
    @Composable
    fun AuthPrimaryButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepRed.copy(alpha = 0.85f),
                contentColor = Color.White
            )
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                fontFamily = DescriptionFont
            )
        }
    }
}
