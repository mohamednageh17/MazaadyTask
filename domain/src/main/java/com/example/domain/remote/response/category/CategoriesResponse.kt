package com.example.domain.remote.response.category

import com.google.gson.annotations.SerializedName


data class CategoriesResponse(

    @SerializedName("code") var code: Int? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: Data? = Data()

)