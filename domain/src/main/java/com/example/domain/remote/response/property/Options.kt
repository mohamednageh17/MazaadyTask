package com.example.domain.remote.response.property

import com.example.domain.utilis.SpinnerItem
import com.google.gson.annotations.SerializedName


data class Options(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("parent") var parent: Int? = null,
    @SerializedName("child") var child: Boolean? = null

) : SpinnerItem {
    override fun getSpinnerText(): String? = name
}