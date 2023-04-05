package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.UserItemResponse
import com.example.fooddeliveryapp.domain.model.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor(private val cartProductMapper: CartProductMapper) {
    operator fun invoke(response: User): UserItemResponse =
        with(response) {
            return UserItemResponse(
                name = name.orEmpty(),
                number = number.orEmpty(),
                hashPassword = hashPassword.orEmpty(),
                date = date.orEmpty(),
                dateRegistration = dateRegistration.orEmpty(),
                totalSpend = totalSpend ?: 0.0,
                orderHistory = emptyList(),
                orderCount = orderCount ?: 0,
                address = address ?: emptyList(),
                discount = discount ?: 0,
                id = id.orEmpty()
            )
        }
}