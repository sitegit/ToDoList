package com.example.todolist.domain.usecase

import com.example.todolist.domain.ToDoEntity
import com.example.todolist.domain.ToDoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetToDoListUseCaseTest {
    private lateinit var getToDoListUseCase: GetToDoListUseCase
    private lateinit var repository: ToDoRepository

    @Before
    fun setUp() {
        repository = mock()
        getToDoListUseCase = GetToDoListUseCase(repository)
    }

    @Test
    fun `invoke returns tasks for the given day`() = runBlocking {
        // Arrange
        val dayStart = 1705813200000L
        val dayEnd = 1705816800000L

        val expectedToDoList = listOf(
            ToDoEntity(
                name = "Task 1",
                startTime = 1705814000000L,
                finishTime = 1705816000000L,
                description = "Description 1"
            ),
            ToDoEntity(
                name = "Task 2",
                startTime = 1705815000000L,
                finishTime = 1705817000000L,
                description = "Description 2"
            )
        )

        whenever(repository.getTasksForDay(dayStart, dayEnd)).thenReturn(flowOf(expectedToDoList))

        // Act
        val result: Flow<List<ToDoEntity>> = getToDoListUseCase(dayStart, dayEnd)

        // Assert
        val resultList = result.toList().flatten()
        assertEquals(expectedToDoList.size, resultList.size)
        assertEquals(expectedToDoList, resultList)
    }
}