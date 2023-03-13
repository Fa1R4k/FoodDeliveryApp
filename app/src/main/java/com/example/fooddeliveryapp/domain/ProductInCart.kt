package com.example.fooddeliveryapp.domain

import androidx.room.ColumnInfo

data class ProductInCart(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val prise: Double,
    val productParameter: String,
    var countProductInCart: Int,
)