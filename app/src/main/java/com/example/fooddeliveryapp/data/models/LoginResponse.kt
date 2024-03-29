package com.example.fooddeliveryapp.data.models

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "number") val number: String? = null,
    @Json(name = "hashPassword") val hashPassword: String? = null,
)