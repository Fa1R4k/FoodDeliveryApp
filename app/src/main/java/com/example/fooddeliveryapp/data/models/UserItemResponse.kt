package com.example.fooddeliveryapp.data.models

import com.squareup.moshi.Json

data class UserItemResponse(
    @Json(name = "name") var name: String? = null,
    @Json(name = "number") var number: String? = null,
    @Json(name = "hashPassword") var hashPassword: String? = null,
    @Json(name = "date") val date: String? = null,
    @Json(name = "dateRegistration") val dateRegistration: String? = null,
    @Json(name = "totalSpend") val totalSpend: Double? = null,
    @Json(name = "orderHistory") var orderHistory: List<HistoryOrderResponse>? = null,
    @Json(name = "orderCount") var orderCount: Int? = null,
    @Json(name = "address") var address: List<String>? = null,
    @Json(name = "nextDiscountSum") var nextDiscountSum: Double? = null,
    @Json(name = "discount") var discount: Int? = null,
    @Json(name = "id") var id: String? = null,
)