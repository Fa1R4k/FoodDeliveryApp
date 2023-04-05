package com.example.fooddeliveryapp

import com.example.fooddeliveryapp.domain.UseCase.UpdateDiscountUseCase
import com.example.fooddeliveryapp.domain.model.User
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestUpdateDiscountUseCase {
    @Test
    fun `test update discount use case`() {

        val user = User("max","123","123","","", totalSpend = 175000.0)
        println(user.totalSpend)
        val useCase = UpdateDiscountUseCase()
        println(useCase.execute(user).nextDiscountSum.toString() + "!!!!!!!!!!!!!!!")
        assertEquals("-1.0", useCase.execute(user).nextDiscountSum.toString())
    }
}