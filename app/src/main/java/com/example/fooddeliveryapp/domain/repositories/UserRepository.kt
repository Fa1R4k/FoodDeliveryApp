package com.example.fooddeliveryapp.domain.repositories

import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.User

interface UserRepository {

    suspend fun isAuthorized(): Boolean
    suspend fun createUser(user: User): Boolean
    suspend fun loginUser(userNumber: String, password: String): Boolean
    suspend fun getUser(): User
    fun logout()
    suspend fun updateUser(user: User): Boolean
    suspend fun updateUser(
        user: User,
        order: List<CartProduct>,
        date: String,
        totalCount: Double,
    ): Boolean
}