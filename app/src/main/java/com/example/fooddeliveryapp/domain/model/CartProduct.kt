package com.example.fooddeliveryapp.domain.model

data class CartProduct(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val prise: Double,
    val productParameter: String,
    var countProductInCart: Int
)