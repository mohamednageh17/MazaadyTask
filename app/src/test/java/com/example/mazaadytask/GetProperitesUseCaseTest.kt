package com.example.mazaadytask

import com.example.domain.repo.MazaadyRepository
import com.example.domain.remote.response.property.Property
import com.example.domain.usecase.GetPropertiesUseCase
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
class GetPropertiesUseCaseTest {

    private lateinit var repository: MazaadyRepository
    private lateinit var getPropertiesUseCase: GetPropertiesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getPropertiesUseCase = GetPropertiesUseCase(repository)
    }

    @Test
    fun `invoke should return properties list`() = runTest {
        // Arrange: mock data
        val mockProperties = listOf(Property(id = 1, name = "Property 1"))

        // Mock the repository function
        coEvery { repository.getProperties(1) } returns flowOf(mockProperties)

        // Act: call the use case's invoke method
        val result = getPropertiesUseCase.invoke(1)

        // Assert: verify that the result matches the mock data
        assertThat(result).isNotNull()
        assertThat(result.first()).isEqualTo(mockProperties)
    }
}
