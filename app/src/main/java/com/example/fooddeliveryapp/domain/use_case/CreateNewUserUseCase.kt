package com.example.fooddeliveryapp.domain.use_case

import com.example.fooddeliveryapp.domain.model.User
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(
    private val hashingPasswordUseCase: HashingPasswordUseCase,
    private val getCurrentDate: GetCurrentDateUseCase,
) {
    fun execute(userName: String, userNumber: String, userPassword: String, date: String): User {
        val hashPassword = hashingPasswordUseCase.execute(userPassword)
        return User(userName,
            userNumber,
            hashPassword,
            date,
            getCurrentDate.execute())
    }
}