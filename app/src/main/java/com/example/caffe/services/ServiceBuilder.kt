package com.example.caffe.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://coffee-server-nodejs.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}