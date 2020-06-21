package com.example.caffe.services

import com.example.caffe.model.CoffeeModel
import com.example.caffe.model.OrderModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OrderApiService {
    fun sendOrder(orderData: OrderModel, onResult: (OrderModel?) -> Unit) {
        val createCall = ServiceBuilder.buildService(ApiCoffee::class.java)
        createCall.addOrder(orderData).enqueue(
            object : Callback<OrderModel>{
                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    val addOrder = response.body()
                    onResult(addOrder)
                }
            }
        )
    }
}