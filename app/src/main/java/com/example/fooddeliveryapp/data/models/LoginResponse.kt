package com.example.fooddeliveryapp.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("number")val number: String,
    @SerializedName("hashPassword")val hashPassword: String
) {

}