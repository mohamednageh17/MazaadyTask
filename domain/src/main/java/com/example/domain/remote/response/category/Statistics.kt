package com.example.domain.remote.response.category

import com.google.gson.annotations.SerializedName


data class Statistics(

    @SerializedName("auctions") var auctions: Int? = null,
    @SerializedName("users") var users: Int? = null,
    @SerializedName("products") var products: Int? = null

)