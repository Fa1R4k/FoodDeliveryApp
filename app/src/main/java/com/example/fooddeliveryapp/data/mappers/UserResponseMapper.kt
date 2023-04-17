package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.UserItemRequest
import com.example.fooddeliveryapp.domain.model.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor(
    private val historyOrderResponseMapper: HistoryOrderResponseMapper,
) {
    operator fun invoke(model: User): UserItemRequest = with(model) {
        return UserItemRequest(
            name = name,
            number = number,
            hashPassword = hashPassword,
            date = date,
            dateRegistration = dateRegistration,
            totalSpend = totalSpend,
            orderHistory = orderHistory.map { historyOrderResponseMapper(it) },
            orderCount = orderCount,
            address = address,
            nextDiscountSum = nextDiscountSum,
            discount = discount,
            id = id
        )
    }
}