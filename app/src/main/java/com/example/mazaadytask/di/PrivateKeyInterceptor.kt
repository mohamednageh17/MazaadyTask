package com.example.mazaadytask.di

import com.example.mazaadytask.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PrivateKeyInterceptor @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()

        request.header("private-key", BuildConfig.PRIVATE_KEY)

//        request.header("lang", lang)

        request.method(original.method, original.body)

        return chain.proceed(request.build())

    }
}