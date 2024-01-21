package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetToDoInfoUseCaseTest {

    private lateinit var getToDoInfoUseCase: GetToDoInfoUseCase
    private lateinit var repository: ToDoRepository

    @Before
    fun setUp() {
        repository = mock()
        getToDoInfoUseCase = GetToDoInfoUseCase(repository)
    }

    @Test
    fun `invoke retrieves task from repository`() = runBlocking {

        // Arrange
        val taskId = 3
        val expectedToDoEntity = ToDoEntity(
            name = "title",
            startTime = 1705816800000L,
            finishTime = 1705816800000L,
            description = "description"
        )
        whenever(repository.getTask(taskId)).thenReturn(expectedToDoEntity)

        // Act
        val result = getToDoInfoUseCase.invoke(taskId)

        // Assert
        assertEquals(expectedToDoEntity.name, result.name, result.name)
        assertEquals(expectedToDoEntity.startTime, result.startTime)
        assertEquals(expectedToDoEntity.startTime, result.finishTime)
        assertEquals(expectedToDoEntity.description, result.description, result.description)
    }
}
