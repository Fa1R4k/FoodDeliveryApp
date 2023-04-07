package com.example.fooddeliveryapp.domain.use_case

import com.example.fooddeliveryapp.domain.model.CartProduct
import javax.inject.Inject

class GetPriceFromCartDataBaseUseCase @Inject constructor() {

    fun execute(cartProductList: List<CartProduct>): Double {
        var price = 0.0
        cartProductList.map { price += it.prise * it.countProductInCart }
        return price
    }
}