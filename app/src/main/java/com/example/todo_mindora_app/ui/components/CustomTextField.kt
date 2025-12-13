package com.example.todo_mindora_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo_mindora_app.ui.theme.TextColor

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    height: Int = 56,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp),
            fontSize = 14.sp
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            singleLine = height == 56,
            colors = TextFieldDefaults.colors(

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,


                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,


                focusedTextColor = TextColor,
                unfocusedTextColor = TextColor.copy(alpha = 0.8f),

                focusedPlaceholderColor = Color.Gray.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = Color.Gray.copy(alpha = 0.5f)
            ),
            placeholder = {
                Text(text = label, color = Color.Gray.copy(alpha = 0.4f), fontSize = 14.sp)
            }
        )
    }
}