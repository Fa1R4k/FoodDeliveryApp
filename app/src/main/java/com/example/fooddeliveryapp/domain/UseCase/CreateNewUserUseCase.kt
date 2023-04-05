package com.example.fooddeliveryapp.domain.UseCase

import com.example.fooddeliveryapp.domain.model.User
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateNewUserUseCase @Inject constructor(
    private val hashingPasswordUseCase: HashingPasswordUseCase
) {
    fun execute(userName: String, userNumber: String, userPassword: String, date: String): User {
        val hashPassword = hashingPasswordUseCase.execute(userPassword)
        return User(userName,
            userNumber,
            hashPassword,
            date,
            getCurrentData())
    }

    private fun getCurrentData(): String {
        val dateReg = Date()
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(dateReg)
    }
}