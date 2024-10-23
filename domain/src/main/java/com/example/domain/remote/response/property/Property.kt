package com.example.domain.remote.response.property

import com.example.domain.utilis.SpinnerItem
import com.google.gson.annotations.SerializedName


data class Property(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("parent") var parent: String? = null,
    @SerializedName("list") var list: Boolean? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("other_value") var otherValue: String? = null,
    @SerializedName("options") var options: ArrayList<Options> = arrayListOf(),
    var selectOtherValue: Boolean = false,
    var selectedOption: Options? = null,
    var childProperties: List<Property> = arrayListOf()
) : SpinnerItem {
    override fun getSpinnerText(): String? = name
}