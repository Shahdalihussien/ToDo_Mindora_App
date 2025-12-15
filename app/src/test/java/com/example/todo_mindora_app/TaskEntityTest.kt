package com.example.todo_mindora_app

import com.example.todo_mindora_app.data.local.entity.TaskEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskEntityTest {

    @Test
    fun taskEntity_fieldsAreSavedCorrectly() {

        val task = TaskEntity(
            id = 1,
            title = "Study Kotlin",
            description = "Unit testing basics",
            date = "2025-12-15",
            startTime = "10:00 AM",
            endTime = "12:00 PM",
            priority = "High",
            category = "Study",
            color = 0xFF0000.toInt()
        )

        assertEquals("Study Kotlin", task.title)
        assertEquals("High", task.priority)
        assertEquals("Study", task.category)
    }
}
