package com.example.todo_mindora_app


import com.example.todo_mindora_app.data.local.entity.TaskEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class TaskViewModelMockitoTest {

    @Test
    fun mockTask_returnsCorrectTitle() {
        val task = mock(TaskEntity::class.java)

        `when`(task.title).thenReturn("Mockito Task")

        assertEquals("Mockito Task", task.title)
    }
}
