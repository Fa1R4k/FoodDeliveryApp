package com.example.fooddeliveryapp.domain.UseCase

import com.example.fooddeliveryapp.domain.model.User
import javax.inject.Inject

class UpdateDiscountUseCase @Inject constructor() {
    fun execute(user: User): User {
        val discounts = mapOf(
            0 to 0.0,
            1 to 50.0,
            2 to 100.0,
            3 to 150.0,
            4 to 400.0,
            5 to 800.0,
            6 to 1200.0,
            7 to 1800.0,
            8 to 2500.0,
            9 to 3000.0,
            10 to 3500.0,
            11 to 4000.0,
            12 to 5500.0,
            13 to 6000.0,
            14 to 8000.0,
            15 to 10000.0
        )

        var discountPercent = 0
        var nextDiscountSum = 0.0
        val userSum = user.totalSpend

        for ((percent, sum) in discounts) {
            if (percent == 15) {
                discountPercent = percent
                nextDiscountSum = -1.0
                break
            }
            if (userSum <= sum) {
                break
            } else {
                discountPercent = percent
                nextDiscountSum = (discounts[percent + 1] ?: 0.0) - userSum
            }
        }
        user.discount = discountPercent
        user.nextDiscountSum = nextDiscountSum
        return user
    }
}
