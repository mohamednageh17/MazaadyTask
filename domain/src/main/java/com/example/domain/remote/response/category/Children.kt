package com.example.domain.remote.response.category

import com.example.domain.utilis.SpinnerItem
import com.google.gson.annotations.SerializedName


data class Children(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("children") var children: String? = null,
    @SerializedName("circle_icon") var circleIcon: String? = null,
    @SerializedName("disable_shipping") var disableShipping: Int? = null

):SpinnerItem{
    override fun getSpinnerText(): String? =name
}