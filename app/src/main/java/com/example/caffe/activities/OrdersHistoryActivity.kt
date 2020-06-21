package com.example.caffe.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.BottomNavigationMenuLogic
import com.example.caffe.R
import com.example.caffe.adapters.RecyclerViewAdapterHistory
import com.example.caffe.db.DAO.OrdersHistoryDao
import com.example.caffe.db.databases.CoffeeDatabase
import com.example.caffe.db.entities.UserNameEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_orders_history.*

class OrdersHistoryActivity : BottomNavigationMenuLogic(2) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerViewAdapterHistory
    private var db: CoffeeDatabase? = null
    private var historyDao: OrdersHistoryDao? = null
    private val TAG = "OrdersHistoryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_history)
        val refreshIntent = Intent(this, OrdersHistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        getCustomerName()

        change_name.visibility = View.INVISIBLE
        save_name_button.visibility = View.INVISIBLE

        change_name_button.setOnClickListener{
            change_name.visibility = View.VISIBLE
            save_name_button.visibility = View.VISIBLE
        }

        save_name_button.setOnClickListener{
            change_name.visibility = View.INVISIBLE
            save_name_button.visibility = View.INVISIBLE
            saveCustomerName()
            Toast.makeText(applicationContext,"Name changed successfully", Toast.LENGTH_LONG).show()

            startActivity(refreshIntent)
        }
        recyclerView = findViewById(R.id.history_list)
        recyclerAdapter = RecyclerViewAdapterHistory(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        Observable.fromCallable({
            db = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
            historyDao = db?.orderHistoryDao()
            historyDao?.getOrdersHistory()?.forEach(){
                recyclerAdapter.setHistoryListItem(historyDao!!.getOrdersHistory())
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()


        setUpBottomNavigation()
    }

    private fun saveCustomerName(){
        Observable.fromCallable({
            db = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
            historyDao = db?.orderHistoryDao()
            historyDao!!.deleteName()
            val userName = UserNameEntity(name = change_name.text.toString())
            with(historyDao){
                this?.insertUserName(userName)
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
    private fun getCustomerName(){
        Observable.fromCallable({
            db = CoffeeDatabase.getCoffeeOrderDatabase(context = this)
            historyDao = db?.orderHistoryDao()
            historyDao?.getName()?.forEach{
            current_user_name.text = it.name
            }

        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
