package com.example.data.remote.api

import com.example.domain.remote.response.BaseResponse

import com.example.domain.remote.response.category.Data
import com.example.domain.remote.response.property.Property
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MazaaddyApiService {
    @GET("get_all_cats")
    suspend fun getAllCategories(): Response<BaseResponse<Data>>

    @GET("properties")
    suspend fun getProperties(
        @Query("cat") cat: Int,
    ): Response<BaseResponse<List<Property>>>

    @GET("get-options-child/{id}")
    suspend fun getOptionsChild(
        @Path("id") id: Int,
    ): Response<BaseResponse<List<Property>>>

}