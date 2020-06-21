package com.example.caffe.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrdersHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "coffee_name")
    val name: String,
    @ColumnInfo(name = "coffee_volume")
    val volume: String,
    @ColumnInfo(name = "coffee_price")
    val price: String
)