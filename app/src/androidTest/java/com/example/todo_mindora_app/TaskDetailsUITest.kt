package com.example.todo_mindora_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.todo_mindora_app.ui.screens.task.DetailCard
import org.junit.Rule
import org.junit.Test

class TaskDetailsUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailCard_displaysCorrectValue() {

        composeTestRule.setContent {
            DetailCard(
                label = "Title",
                value = "My Test Task"
            )
        }

        composeTestRule
            .onNodeWithText("My Test Task")
            .assertIsDisplayed()
    }
}
