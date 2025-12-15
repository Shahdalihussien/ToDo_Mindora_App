package com.example.todo_mindora_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import com.example.todo_mindora_app.ui.screens.task.EditTaskScreenContent


class EditTaskUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun editTaskScreen_displaysSaveButton() {
        composeTestRule.setContent {
            EditTaskScreenContent()
        }

        composeTestRule
            .onNodeWithText("Save Changes")
            .assertIsDisplayed()
    }
}
