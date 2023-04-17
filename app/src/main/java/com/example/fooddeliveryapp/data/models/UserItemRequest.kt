package com.example.fooddeliveryapp.data.models

import com.squareup.moshi.Json

data class UserItemRequest(
    @Json(name = "name") val name: String? = null,
    @Json(name = "number") val number: String? = null,
    @Json(name = "hashPassword") val hashPassword: String? = null,
    @Json(name = "date") val date: String? = null,
    @Json(name = "dateRegistration") val dateRegistration: String? = null,
    @Json(name = "totalSpend") val totalSpend: Double? = null,
    @Json(name = "orderHistory") val orderHistory: List<HistoryOrderResponse>? = null,
    @Json(name = "orderCount") val orderCount: Int? = null,
    @Json(name = "address") val address: List<String>? = null,
    @Json(name = "nextDiscountSum") val nextDiscountSum: Double? = null,
    @Json(name = "discount") val discount: Int? = null,
    @Json(name = "id") val id: String? = null,
)