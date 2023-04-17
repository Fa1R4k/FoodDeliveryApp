package com.example.fooddeliveryapp.data.repositoriesImpl

import com.example.fooddeliveryapp.data.mappers.*
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.network.UserService
import com.example.fooddeliveryapp.data.source.UserDataSource
import com.example.fooddeliveryapp.domain.repositories.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import com.example.fooddeliveryapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: UserDataSource,
    private val userResponseMapper: UserResponseMapper,
    private val userMapper: UserMapper,
    private val userService: UserService
) : UserRepository {
    override suspend fun isAuthorized(): Boolean =
        withContext(Dispatchers.IO) {
            userService.getAllUser().map { it.id }.contains(sharedPreferences.getUserToken())
        }

    override suspend fun createUser(user: User): Boolean = withContext(Dispatchers.IO) {
        val isSuccess = userService.createUser(userResponseMapper(user)).isSuccess
        if (isSuccess == true) sharedPreferences.setUserToken(user.id)
        isSuccess == true
    }


    override suspend fun loginUser(userNumber: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val authenticationResponse = userService.login(LoginResponse(userNumber, password))
            if (authenticationResponse.isSuccess == true) sharedPreferences.setUserToken(
                authenticationResponse.user?.id.toString()
            )
            authenticationResponse.isSuccess == true
        }

    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        userMapper(userService.getUserById(sharedPreferences.getUserToken()))
    }

    override suspend fun updateUser(user: User): Boolean = withContext(Dispatchers.IO) {
        userService.updateUser(userResponseMapper(user))
    }

    override suspend fun updateUser(
        user: User,
        order: List<CartProduct>,
        date: String,
        totalCount: Double,
    ): Boolean = withContext(Dispatchers.IO) {
        user.orderHistory.add(HistoryOrderData(date, order, totalCount))
        userService.updateUser(userResponseMapper(user))
    }

    override fun logout() {
        sharedPreferences.setUserToken(EMPTY_STRING)
    }

    companion object{
        private const val EMPTY_STRING = ""
    }
}
