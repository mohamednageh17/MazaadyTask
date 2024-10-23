package com.example.mazaadytask.di


import com.example.domain.repo.MazaadyRepository
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetOptionsChildUseCase
import com.example.domain.usecase.GetPropertiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun getCategoriesUseCase(repository: MazaadyRepository) = GetCategoriesUseCase(repository)

    @Provides
    @Singleton
    fun getProperitiesUseCase(repository: MazaadyRepository) = GetPropertiesUseCase(repository)

    @Provides
    @Singleton
    fun getOptionsChildUseCase(repository: MazaadyRepository) = GetOptionsChildUseCase(repository)

}