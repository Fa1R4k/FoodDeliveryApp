package com.example.fooddeliveryapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table", primaryKeys = ["productId", "productParameter"])
data class CartEntity(
    @ColumnInfo val productId: Int,
    @ColumnInfo val productName: String,
    @ColumnInfo val productImageLink: String,
    @ColumnInfo val productPrice: Double,
    @ColumnInfo val productParameter: String,
    @ColumnInfo val countProductInCart: Int,
    @ColumnInfo val user: Int,
)
