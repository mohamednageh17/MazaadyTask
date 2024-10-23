package com.example.mazaadytask.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.dailyforecastapp.di.qualifiers.AppBuildType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [ApplicationModule::class])
object OkHttpClientModule {

    private const val OK_HTTP_TIMEOUT = 120L


    @Provides
    @Singleton
    fun providesChuckerInterceptor(@ApplicationContext application: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = application,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(application).collector(chuckerCollector)
            .maxContentLength(250_000L).alwaysReadResponseBody(true).build()
    }

    @Provides
    fun provideOkHttpClient(
        privateKeyInterceptor: PrivateKeyInterceptor,
        chuckerInterceptor: ChuckerInterceptor, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder().connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(privateKeyInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun provideLogger(
    ): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger.DEFAULT
    }

    @Provides
    fun provideHttpLoggingInterceptor(
        @AppBuildType isDebug: Boolean,
        logger: HttpLoggingInterceptor.Logger,
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }


}