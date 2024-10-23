package com.example.domain.remote.response.category

import com.google.gson.annotations.SerializedName


data class Data(

    @SerializedName("categories") var categories: ArrayList<Categories> = arrayListOf(),
    @SerializedName("statistics") var statistics: Statistics? = Statistics(),
    @SerializedName("ads_banners") var adsBanners: ArrayList<AdsBanners> = arrayListOf(),
    @SerializedName("ios_version") var iosVersion: String? = null,
    @SerializedName("ios_latest_version") var iosLatestVersion: String? = null,
    @SerializedName("google_version") var googleVersion: String? = null,
    @SerializedName("huawei_version") var huaweiVersion: String? = null

)