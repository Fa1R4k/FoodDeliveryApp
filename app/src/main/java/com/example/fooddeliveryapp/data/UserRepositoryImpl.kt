package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.mappers.UserMapper
import com.example.fooddeliveryapp.data.mappers.UserResponseMapper
import com.example.fooddeliveryapp.data.models.LoginResponse
import com.example.fooddeliveryapp.data.source.UserDataSource
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: UserDataSource,
    private val userResponseMapper: UserResponseMapper,
    private val userMapper: UserMapper,
    private val userService: UserService,
) : UserRepository {
    override fun isAuthorized(): Boolean = sharedPreferences.getUserToken().isNotEmpty()

    override suspend fun createUser(user: User): Boolean = withContext(Dispatchers.IO) {
        val isSuccess = userService.createUser(userResponseMapper(user)).execute().body()?.isSuccess
            ?: throw Exception()
        if (isSuccess) sharedPreferences.setUserToken(user.id)
        isSuccess
    }


    override suspend fun loginUser(userNumber: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val authenticationResponse =
                userService.login(LoginResponse(userNumber, password)).execute().body()
                    ?: throw Exception()
            if (authenticationResponse.isSuccess)
                sharedPreferences.setUserToken(authenticationResponse.user?.id.toString())
            authenticationResponse.isSuccess
        }

    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        userMapper(userService.getAllUsers(sharedPreferences.getUserToken()).execute().body()
            ?: throw Exception())
    }

    override suspend fun updateUser(user: User) : Boolean = withContext(Dispatchers.IO) {
        println(userResponseMapper(user))
        userService.updateUser(userResponseMapper(user)).execute().body()?: false
    }

    override fun logout() {
        sharedPreferences.setUserToken("")
    }
}
