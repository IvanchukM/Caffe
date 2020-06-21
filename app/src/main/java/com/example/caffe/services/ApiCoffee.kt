package com.example.caffe.services

import com.example.caffe.model.CoffeeModel
import com.example.caffe.model.OrderModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiCoffee {
    @GET("/api/v1/coffee/menu")
    fun getCoffee(): Call<List<CoffeeModel>>

    @POST("/api/v1/coffee/orders")
    fun addOrder(@Body orderInfo: OrderModel): Call<OrderModel>
}