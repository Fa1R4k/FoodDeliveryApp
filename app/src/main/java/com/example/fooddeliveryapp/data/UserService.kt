package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.models.AuthenticationResponse
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.models.UserItemResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {

    @GET("UserFood/get-user-by-id")
    fun getAllUsers(@Query("id") id: String): Call<UserItemResponse>

    @POST("UserFood/login")
    fun login(@Body loginResponse: LoginResponse): Call<AuthenticationResponse>

    @PUT("UserFood/update-user")
    fun updateUser(@Body userResponse: UserItemResponse): Call<Boolean>

    @POST("UserFood/create-user")
    fun createUser(@Body userResponse: UserItemResponse): Call<AuthenticationResponse>
}