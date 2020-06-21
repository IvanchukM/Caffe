package com.example.caffe.model

import com.google.gson.annotations.SerializedName

data class CoffeeModel(

    var name: String? = null,
    @SerializedName("volume_medium")
    var volumeMedium: String? = null,
    @SerializedName("volume_big")
    var volumeBig: String? = null,
    @SerializedName("price_medium")
    var priceMedium: String? = null,
    @SerializedName("price_big")
    var priceBig: String? = null
)