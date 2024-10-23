package com.example.domain.usecase

import com.example.domain.remote.response.property.Property
import com.example.domain.repo.MazaadyRepository
import kotlinx.coroutines.flow.Flow

class GetOptionsChildUseCase  (private val repository: MazaadyRepository
) : BaseUseCase<Int, Flow<List<Property>>> {

    override suspend fun invoke(params: Int): Flow<List<Property>> =
        repository.getOptionsChild(params)

}