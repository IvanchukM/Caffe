package com.example.caffe.db.DAO

import androidx.room.*
import com.example.caffe.db.entities.CoffeeOrderEntity

@Dao
interface CoffeeOrderDao{
    @Insert
    fun insertCoffeeOrder(coffeeOrder: CoffeeOrderEntity)

    @Query("DELETE FROM CoffeeOrderEntity where ID = :id")
    fun deleteCoffeeOrder(id: Int)

    @Query("SELECT * FROM CoffeeOrderEntity ORDER BY coffee_name ASC")
    fun getCoffee(): List<CoffeeOrderEntity>

    @Query("DELETE FROM CoffeeOrderEntity where ID > 0")
    fun deleteAllOrder()
}