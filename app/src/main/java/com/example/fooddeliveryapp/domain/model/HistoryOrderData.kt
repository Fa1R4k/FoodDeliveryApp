package com.example.fooddeliveryapp.domain.model

data class HistoryOrderData(
    val orderTime: String,
    val cartProducts: List<CartProduct>,
    val orderPrice: Double,
)