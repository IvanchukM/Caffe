package com.example.caffe.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.BottomNavigationMenuLogic
import com.example.caffe.R
import com.example.caffe.adapters.RecyclerViewAdapterOrder
import com.example.caffe.db.DAO.CoffeeOrderDao
import com.example.caffe.db.databases.CoffeeDatabase
import com.example.caffe.db.entities.CoffeeOrderEntity
import com.example.caffe.db.entities.OrdersHistoryEntity
import com.example.caffe.model.OrderModel
import com.example.caffe.services.OrderApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import java.text.DecimalFormat

class ShoppingCartActivity : BottomNavigationMenuLogic(1) {
    private val TAG = "ShoppingCartActivity"
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerViewAdapterOrder
    private var db: CoffeeDatabase? = null
    private var orderDao: CoffeeOrderDao? = null
    private val orderList = listOf<CoffeeOrderEntity>()
    private val df = DecimalFormat("#######.###")
    private var name: String = ""
    private var volume: String = ""
    private var price: String = ""
    private var id: Int = 0
    private var userName = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        val intent: Intent = intent
        var orderSum: Double = 0.0

        recyclerView = findViewById(R.id.order_list)

        recyclerAdapter = RecyclerViewAdapterOrder(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.setOrderListItem(orderList)

            Observable.fromCallable({
                db = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
                orderDao = db?.coffeeOrderDao()

                // add data to db
                if(intent.getStringExtra("name") != null) {
                    // handle intents
                    name = intent.getStringExtra("name")
                    volume = intent.getStringExtra("volume")
                    price = intent.getStringExtra("price")
                    val order =
                        CoffeeOrderEntity(name = name, volume = volume, price = price)
                    with(orderDao) {
                        this?.insertCoffeeOrder(order)
                    }
                }

                //delete order item from db
               if( intent.getStringExtra("id") != null){
                   id = intent.getStringExtra("id").toInt()
                   orderDao?.deleteCoffeeOrder(id)
            }

                // get data from db and put in into recyclerView
                orderDao?.getCoffee()?.forEach(){
                    orderSum += it.price.toDouble()
                }
                if(orderSum == 0.0){
                    empty_order_list_text.text = "Your order list is empty"
                    total_sum_text.text = " "
                    finish_order_button.visibility = View.INVISIBLE
                }else{
                    recyclerAdapter.setOrderListItem(orderDao!!.getCoffee())
                    total_order_sum.text = "${df.format(orderSum)} BYN"
                    finish_order_button.visibility = View.VISIBLE
                }

                 val db2 = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
                 val orderDao = db2?.coffeeOrderDao()
                 val orderHistoryDao = db2?.orderHistoryDao()
                // get user name
                orderHistoryDao?.getName()?.forEach(){
                    userName = it.name
                }

            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        finish_order_button.setOnClickListener{
            if(userName != " "){
                confirmOrder()
            }else{
                val refreshIntent = Intent(this, OrdersHistoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(refreshIntent)
                Toast.makeText(applicationContext,"Change your name please",Toast.LENGTH_SHORT).show()
            }
            Log.d(TAG, "onCreate: name - $userName")

        }

        setUpBottomNavigation()

    }
    private fun sendOrder(customerName: String, coffeeName: String,
                          coffeePrice: String,coffeeVolume: String) {
        val apiService = OrderApiService()
        val orderInfo = OrderModel(
            customerName = customerName,
            coffeeName = coffeeName,
            coffeePrice = coffeePrice,
            coffeeVolume = coffeeVolume
        )
        apiService.sendOrder(orderInfo){}
    }

    private fun confirmOrder(){
        lateinit var name: String
        lateinit var volume: String
        lateinit var price: String
        Observable.fromCallable({

            val db = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
            val orderDao = db?.coffeeOrderDao()
            val orderHistoryDao = db?.orderHistoryDao()

                // delete previous data
                orderHistoryDao?.deleteOrdersHistory()
                orderDao?.getCoffee()?.forEach() {
                    name = it.name
                    volume = it.volume
                    price = it.price
                    val insertData =
                        OrdersHistoryEntity(name = name, volume = "$volume ml", price = "$price BYN")

                    sendOrder(userName,name,price,volume)

                    // set new order history data
                    with(orderHistoryDao) {
                        this?.insertOrdersHistory(insertData)
                    }
                    orderDao.deleteAllOrder()
                }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        val refreshIntent = Intent(this, ShoppingCartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(refreshIntent)
        Toast.makeText(applicationContext,"Your order Confirmed",Toast.LENGTH_SHORT).show()
    }
}
