package com.example.fooddeliveryapp.data.models

import com.google.gson.annotations.SerializedName

data class UserItemResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("number") var number: String? = null,
    @SerializedName("hashPassword") var hashPassword: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("dateRegistration") val dateRegistration: String? = null,
    @SerializedName("totalSpend") val totalSpend: Double? = null,
    @SerializedName("orderHistory") var orderHistory: List<HistoryOrderResponse>? = null,
    @SerializedName("orderCount") var orderCount: Int? = null,
    @SerializedName("address") var address: List<String>? = null,
    @SerializedName("nextDiscountSum") var nextDiscountSum: Double? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("id") var id: String? = null,
)