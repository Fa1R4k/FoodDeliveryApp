package com.example.fooddeliveryapp.data.models

class HistoryOrderResponse(
    val orderTime: String? = null,
    val cartProductItemResponse: List<CartProductItemResponse>? = null,
) {
}