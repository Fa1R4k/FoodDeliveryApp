package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.UserItemResponse
import com.example.fooddeliveryapp.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor(private val historyOrderDataMapper: HistoryOrderDataMapper) {
    operator fun invoke(response: UserItemResponse): User =
        with(response) {
            return User(
                name = name.orEmpty(),
                number = number.orEmpty(),
                hashPassword = hashPassword.orEmpty(),
                date = date.orEmpty(),
                dateRegistration = dateRegistration.orEmpty(),
                totalSpend = totalSpend ?: 0.0,
                orderHistory = orderHistory?.map { historyOrderDataMapper(it) }?.toMutableList() ?: mutableListOf(),
                orderCount = orderCount ?: 0,
                address = address?.toMutableList() ?: mutableListOf(),
                nextDiscountSum = nextDiscountSum ?: 50.0,
                discount = discount ?: 0,
                id = id.orEmpty()
            )
        }
}