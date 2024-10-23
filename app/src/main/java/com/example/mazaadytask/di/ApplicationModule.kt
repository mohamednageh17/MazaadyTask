package com.example.mazaadytask.di

import android.app.Application
import android.content.Context
import com.example.dailyforecastapp.di.qualifiers.AppBuildType
import com.example.dailyforecastapp.di.qualifiers.AppContext
import com.example.dailyforecastapp.di.qualifiers.MazaadyBaseURL
import com.example.mazaadytask.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @AppContext
    @Provides
    fun context(application: Application): Context {
        return application.applicationContext
    }

    @MazaadyBaseURL
    @Provides
    fun provideCitiesBaseURl(): String = BuildConfig.BASE_URL

    @Provides
    fun providePrivateKey(): String = BuildConfig.PRIVATE_KEY

    @AppBuildType
    @Provides
    fun provideBuildType(): Boolean {
        return BuildConfig.DEBUG
    }

}