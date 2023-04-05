package com.example.fooddeliveryapp.data.models

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("result")val user: UserItemResponse? = null,
    @SerializedName("isSuccess")val isSuccess: Boolean = false,
)