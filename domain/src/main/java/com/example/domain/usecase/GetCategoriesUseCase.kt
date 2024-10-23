package com.example.domain.usecase

import com.example.domain.remote.response.category.Categories
import com.example.domain.repo.MazaadyRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase  (private val repository: MazaadyRepository
) : BaseUseCase<Void?, Flow<List<Categories>>> {

    override suspend fun invoke(params: Void?): Flow<List<Categories>> =
        repository.getAllCategories()

}