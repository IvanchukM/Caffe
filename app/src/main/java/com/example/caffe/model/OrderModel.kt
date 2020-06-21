package com.example.caffe.model

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("customer_name")
    var customerName: String? = null,

    @SerializedName("coffee_name")
    var coffeeName: String? = null,

    @SerializedName("coffee_price")
    var coffeePrice: String? = null,

    @SerializedName("coffee_volume")
    var coffeeVolume: String? = null
)