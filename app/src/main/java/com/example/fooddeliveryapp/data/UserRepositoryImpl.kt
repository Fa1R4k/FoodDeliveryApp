package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.mappers.CartProductItemMapper
import com.example.fooddeliveryapp.data.mappers.UserMapper
import com.example.fooddeliveryapp.data.mappers.UserResponseMapper
import com.example.fooddeliveryapp.data.models.HistoryOrderResponse
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.source.UserDataSource
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: UserDataSource,
    private val userResponseMapper: UserResponseMapper,
    private val userMapper: UserMapper,
    private val userService: UserService,
    private val cartProductItemMapper: CartProductItemMapper,
) : UserRepository {
    override fun isAuthorized(): Boolean = sharedPreferences.getUserToken().isNotEmpty()

    override suspend fun createUser(user: User): Boolean = withContext(Dispatchers.IO) {
        val isSuccess = userService.createUser(userResponseMapper(user)).isSuccess
        if (isSuccess) sharedPreferences.setUserToken(user.id)
        isSuccess
    }


    override suspend fun loginUser(userNumber: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val authenticationResponse = userService.login(LoginResponse(userNumber, password))
            if (authenticationResponse.isSuccess) sharedPreferences.setUserToken(
                authenticationResponse.user?.id.toString())
            authenticationResponse.isSuccess
        }

    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        userService.getUserById(sharedPreferences.getUserToken()).orderHistory?.map { println( it.cartProductItemResponse.toString() + " time:  " + it.orderTime.toString()) }
        userMapper(userService.getUserById(sharedPreferences.getUserToken()))
    }

    override suspend fun updateUser(user: User): Boolean = withContext(Dispatchers.IO) {
        userService.updateUser(userResponseMapper(user))
    }

    override suspend fun updateUser(user: User, order: List<CartProduct>, date: String): Boolean =
        withContext(Dispatchers.IO) {
            userResponseMapper(user).apply {
                orderHistory?.add(HistoryOrderResponse(date,
                    order.map { cartProductItemMapper(it) })) ?: run {
                    orderHistory = mutableListOf(HistoryOrderResponse(date,
                        order.map { cartProductItemMapper(it) }))
                }
            }.let {
                userService.updateUser(it)
            }
        }

    override fun logout() {
        sharedPreferences.setUserToken("")
    }
}
