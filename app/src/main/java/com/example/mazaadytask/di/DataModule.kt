package com.example.mazaadytask.di

import com.example.dailyforecastapp.di.qualifiers.MazaadyBaseURL
import com.example.data.remote.api.MazaaddyApiService
import com.example.data.repo.MazaadyRepositoryImpl
import com.example.domain.repo.MazaadyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Binds
    @Singleton
    abstract fun bindMazaadyRepository(repositoryImpl: MazaadyRepositoryImpl): MazaadyRepository

}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMazaadyRepositoryImpl(cityApiService: MazaaddyApiService) =
        MazaadyRepositoryImpl(cityApiService)


    @Provides
    @Singleton
    fun provideCitiesApiService(
        @MazaadyBaseURL baseUrl: String,
        okHttpClient: OkHttpClient,
    ): MazaaddyApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MazaaddyApiService::class.java)
    }

}