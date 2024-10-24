package com.example.mazaadytask

import com.example.domain.repo.MazaadyRepository
import com.example.domain.remote.response.property.Property
import com.example.domain.usecase.GetOptionsChildUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetOptionsChildUseCaseTest {

    private lateinit var repository: MazaadyRepository
    private lateinit var getOptionsChildUseCase: GetOptionsChildUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getOptionsChildUseCase = GetOptionsChildUseCase(repository)
    }

    @Test
    fun `invoke should return child properties list`() = runTest {
        // Arrange: mock data
        val mockChildProperties = listOf(Property(id = 1, name = "Child Property 1"))

        // Mock the repository function
        coEvery { repository.getOptionsChild(1) } returns flowOf(mockChildProperties)

        // Act: call the use case's invoke method
        val result = getOptionsChildUseCase.invoke(1)

        // Assert: verify that the result matches the mock data
        assertThat(result).isNotNull()
        assertThat(result.first()).isEqualTo(mockChildProperties)
    }
}
