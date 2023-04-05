package com.example.fooddeliveryapp.data.models

data class ProductItemResponse(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val category: String? = null,
    var price: Map<String, Double>? = null,
)
