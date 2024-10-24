package com.example.mazaadytask

import com.example.domain.repo.MazaadyRepository
import com.example.domain.remote.response.category.Categories
import com.example.domain.usecase.GetCategoriesUseCase
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
class GetCategoriesUseCaseTest {

    private lateinit var repository: MazaadyRepository
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        getCategoriesUseCase = GetCategoriesUseCase(repository)
    }

    @Test
    fun `invoke should return categories list`() = runTest {
        // Arrange: mock data
        val mockCategories = listOf(Categories(id = 1, name = "Category 1"))

        // Mock the repository function
        coEvery { repository.getAllCategories() } returns flowOf(mockCategories)

        // Act: call the use case's invoke method
        val result = getCategoriesUseCase.invoke(null)

        // Assert: verify that the result matches the mock data
        assertThat(result).isNotNull()
        assertThat(result.first()).isEqualTo(mockCategories)
    }
}
