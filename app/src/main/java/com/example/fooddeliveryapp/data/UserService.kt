package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.models.AuthenticationResponse
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.models.UserItemResponse
import retrofit2.http.*

interface UserService {

    @GET("UserFood/get-user-by-id")
    suspend fun getUserById(@Query("id") id: String): UserItemResponse

    @POST("UserFood/login")
    suspend fun login(@Body loginResponse: LoginResponse): AuthenticationResponse

    @PUT("UserFood/update-user")
    suspend fun updateUser(@Body userResponse: UserItemResponse): Boolean

    @POST("UserFood/create-user")
    suspend fun createUser(@Body userResponse: UserItemResponse): AuthenticationResponse
}