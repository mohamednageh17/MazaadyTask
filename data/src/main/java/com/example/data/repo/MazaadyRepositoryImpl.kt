package com.example.data.repo

import com.example.data.remote.api.MazaaddyApiService
import com.example.data.utilis.getResponse
import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.property.Property
import com.example.domain.repo.MazaadyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MazaadyRepositoryImpl(
    private val apiService: MazaaddyApiService
) : MazaadyRepository {

    override suspend fun getAllCategories(): Flow<List<Categories>> = flow {
        emit(apiService.getAllCategories().getResponse().data?.categories ?: emptyList())
    }

    override suspend fun getProperties(catId: Int): Flow<List<Property>> = flow {
        emit(apiService.getProperties(cat = catId).getResponse().data ?: emptyList())
    }

    override suspend fun getOptionsChild(id: Int): Flow<List<Property>>  = flow {
        emit(apiService.getOptionsChild(id = id).getResponse().data ?: emptyList())
    }
}