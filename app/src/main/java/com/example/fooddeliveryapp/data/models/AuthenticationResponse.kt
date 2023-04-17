package com.example.fooddeliveryapp.data.models

import com.squareup.moshi.Json

data class AuthenticationResponse(
    @Json(name = "result") val user: UserItemRequest? = null,
    @Json(name = "isSuccess") val isSuccess: Boolean? = null,
)