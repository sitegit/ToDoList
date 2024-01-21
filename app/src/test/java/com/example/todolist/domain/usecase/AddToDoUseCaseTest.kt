package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class AddToDoUseCaseTest {

    private lateinit var addToDoUseCase: AddToDoUseCase
    private lateinit var repository: ToDoRepository

    @Before
    fun setUp() {
        repository = mock()
        addToDoUseCase = AddToDoUseCase(repository)
    }

    @Test
    fun `invoke adds task to repository`() = runBlocking {

        // Arrange
        val currentTime = System.currentTimeMillis()
        val oneHourLater = currentTime + 60 * 60 * 1000

        val toDoEntity = ToDoEntity(
            name = "Title",
            startTime = currentTime,
            finishTime = oneHourLater,
            description = "description"
        )

        // Act
        addToDoUseCase.invoke(toDoEntity)

        // Assert
        verify(repository).addTask(toDoEntity)
    }
}