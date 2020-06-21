package com.example.caffe.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.BottomNavigationMenuLogic
import com.example.caffe.R
import com.example.caffe.adapters.RecyclerViewAdapterMenu
import com.example.caffe.model.CoffeeModel
import com.example.caffe.services.ApiCoffee
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MenuActivity : BottomNavigationMenuLogic(0) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerViewAdapterMenu
    private val TAG = "MenuActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        // check for internet connection
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if(networkInfo == null || !networkInfo.isConnected){
            Toast.makeText(applicationContext,"No Internet Connection",Toast.LENGTH_LONG).show()
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://coffee-server-nodejs.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val coffeeApi: ApiCoffee = retrofit.create(ApiCoffee::class.java)

        recyclerView = findViewById(R.id.coffee_list)
        recyclerAdapter = RecyclerViewAdapterMenu(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        val progressBar = progressBar
        val createCall: Call<List<CoffeeModel>> = coffeeApi.getCoffee()
        createCall.enqueue(object : Callback<List<CoffeeModel>> {
            @Override
            override fun onResponse(
                teml: Call<List<CoffeeModel>>,
                resp: Response<List<CoffeeModel>>
            ) {
                if (resp.body() != null) {
                    recyclerAdapter.setCoffeeListItem(resp.body()!!)
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<CoffeeModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })

        setUpBottomNavigation()

    }
}
