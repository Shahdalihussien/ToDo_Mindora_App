package com.example.todo_mindora_app.ui.screens.auth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.todo_mindora_app.ui.theme.*

object AuthComponents {

@Composable
fun AuthBackground(content: @Composable BoxScope.() -> Unit) {
Box(
        modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
Box(
        modifier = Modifier
                .size(140.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = (-40).dp)
        .clip(CircleShape)
                    .background(SecondaryColor.copy(alpha = 0.25f))
        )
Box(
        modifier = Modifier
                .size(90.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-40).dp, y = 30.dp)
        .clip(CircleShape)
                    .background(PrimaryColor.copy(alpha = 0.18f))
        )

content()
        }
                }

@Composable
fun AuthCard(content: @Composable ColumnScope.() -> Unit) {
Card(
        shape = RoundedCornerShape(24.dp),
elevation = CardDefaults.cardElevation(6.dp),
colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
),
modifier = Modifier
        .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
Column(
        modifier = Modifier
                .padding(20.dp)
                    .fillMaxWidth(),
verticalArrangement = Arrangement.spacedBy(14.dp),
content = content
            )
                    }
                    }

@Composable
fun AuthTitle(
        title: String,
        subtitle: String
) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
    ) {
        Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryColor
        )
        Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryColor
        )
    }
}

@Composable
fun AuthTextField(
        value: String,
        onValueChange: (String) -> Unit,
label: String,
modifier: Modifier = Modifier,
visualTransformation: VisualTransformation = VisualTransformation.None
    ) {
OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
singleLine = true,
modifier = modifier.fillMaxWidth(),
shape = RoundedCornerShape(14.dp),
visualTransformation = visualTransformation,
colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SecondaryColor,
        cursorColor = SecondaryColor
)
        )
                }

@Composable
fun AuthPrimaryButton(
        text: String,
        onClick: () -> Unit,
enabled: Boolean = true
        ) {
Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
shape = RoundedCornerShape(16.dp),
colors = ButtonDefaults.buttonColors(
        containerColor = PrimaryColor,
        contentColor = MaterialTheme.colorScheme.onPrimary
)
        ) {
Text(text)
        }
                }
                }
