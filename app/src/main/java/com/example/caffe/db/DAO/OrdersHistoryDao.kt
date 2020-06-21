package com.example.caffe.db.DAO

import androidx.room.*
import com.example.caffe.db.entities.OrdersHistoryEntity
import com.example.caffe.db.entities.UserNameEntity

@Dao
interface OrdersHistoryDao {
    @Insert
    fun insertOrdersHistory(ordersHistory: OrdersHistoryEntity)

    @Query("DELETE FROM OrdersHistoryEntity WHERE id > 0")
    fun deleteOrdersHistory()

    @Query("SELECT * FROM OrdersHistoryEntity")
    fun getOrdersHistory(): List<OrdersHistoryEntity>
//
//    @Query("INSERT INTO UserNameEntity (user_name) VALUES ()")
    @Insert
    fun insertUserName(userNameEntity: UserNameEntity)

    @Query("SELECT * FROM UserNameEntity")
    fun getName(): List<UserNameEntity>

    @Query("DELETE FROM UserNameEntity WHERE id > 0")
    fun deleteName()
}