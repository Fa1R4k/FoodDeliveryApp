package com.example.fooddeliveryapp.data.network

import com.example.fooddeliveryapp.data.models.AuthenticationResponse
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.models.UserItemRequest
import retrofit2.http.*

interface UserService {

    @GET("UserFood/get-all-users")
    suspend fun getAllUser(): List<UserItemRequest>

    @GET("UserFood/get-user-by-id")
    suspend fun getUserById(@Query("id") id: String): UserItemRequest

    @POST("UserFood/login")
    suspend fun login(@Body loginResponse: LoginResponse): AuthenticationResponse

    @PUT("UserFood/update-user")
    suspend fun updateUser(@Body userResponse: UserItemRequest): Boolean

    @POST("UserFood/create-user")
    suspend fun createUser(@Body userResponse: UserItemRequest): AuthenticationResponse
}