package com.example.fooddeliveryapp.domain

import com.example.fooddeliveryapp.domain.model.User

interface UserRepository {

    fun isAuthorized(): Boolean
    suspend fun createUser(user: User): Boolean
    suspend fun loginUser(userNumber: String, password: String): Boolean
    suspend fun getUser(): User
    fun logout()
    suspend fun updateUser(user: User): Boolean
}