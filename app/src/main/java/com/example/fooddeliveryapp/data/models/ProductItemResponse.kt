package com.example.fooddeliveryapp.data.models

data class ProductItemResponse(
    val id: Int? = null,
    val imageUrl: String? = null,
    val rating: String? = null,
    val name: String? = null,
    val isOscar: Boolean? = null,
    val description: String? = null,
    val category: String? = null,
    val prise: Map<String, Double>? = null,
)
