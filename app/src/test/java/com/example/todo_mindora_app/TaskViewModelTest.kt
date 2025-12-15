package com.example.todo_mindora_app


import com.example.todo_mindora_app.data.local.entity.TaskEntity
import org.junit.Assert.*
import org.junit.Test

class TaskTests {

    @Test
    fun task_title_isStoredCorrectly() {
        val task = createFakeTask()

        assertEquals("Study Kotlin", task.title)
    }

    @Test
    fun task_priority_isHigh() {
        val task = createFakeTask()

        assertEquals("High", task.priority)
    }

    @Test
    fun task_category_isWork() {
        val task = createFakeTask()

        assertEquals("Work", task.category)
    }

    @Test
    fun task_time_isFormattedCorrectly() {
        val task = createFakeTask()

        val time = "${task.startTime} - ${task.endTime}"
        assertEquals("10:00 AM - 05:00 PM", time)
    }

    @Test
    fun task_color_isNotZero() {
        val task = createFakeTask()

        assertTrue(task.color != 0)
    }

    // helper function
    private fun createFakeTask(): TaskEntity {
        return TaskEntity(
            id = 1,
            title = "Study Kotlin",
            description = "Practice unit testing",
            date = "2025-12-15",
            startTime = "10:00 AM",
            endTime = "05:00 PM",
            priority = "High",
            category = "Work",
            color = 0xFFAA000.toInt()
        )
    }
}
