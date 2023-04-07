package com.example.fooddeliveryapp.data.models

data class CartProductItemResponse(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val prise: Double,
    val productParameter: String,
    var countProductInCart: Int,
)