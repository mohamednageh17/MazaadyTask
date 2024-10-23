package com.example.domain.repo

import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.property.Property
import kotlinx.coroutines.flow.Flow

interface MazaadyRepository {

    suspend fun getAllCategories(): Flow<List<Categories>>

    suspend fun getProperties(catId:Int): Flow<List<Property>>

    suspend fun getOptionsChild(id:Int): Flow<List<Property>>
}