package com.example.caffe.db.databases

import android.content.Context
import androidx.room.*
import com.example.caffe.db.DAO.CoffeeOrderDao
import com.example.caffe.db.DAO.OrdersHistoryDao
import com.example.caffe.db.entities.CoffeeOrderEntity
import com.example.caffe.db.entities.OrdersHistoryEntity
import com.example.caffe.db.entities.UserNameEntity

@Database(entities = arrayOf(
    CoffeeOrderEntity::class,
    OrdersHistoryEntity::class,
    UserNameEntity::class), version = 1)
abstract class CoffeeDatabase: RoomDatabase(){
    abstract fun orderHistoryDao(): OrdersHistoryDao
    abstract fun coffeeOrderDao(): CoffeeOrderDao
    companion object{
        private var INSTANCE: CoffeeDatabase? = null

        fun getCoffeeOrderDatabase(context: Context): CoffeeDatabase?{
            if(INSTANCE == null){
                synchronized(CoffeeDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, CoffeeDatabase::class.java, "ordersDB").build()
                }
            }
            return INSTANCE
        }
        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}