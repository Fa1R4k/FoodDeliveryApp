package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.ProductItem
import javax.inject.Inject

class ProductInCartMapper @Inject constructor() {
    operator fun invoke(
        response: CartEntity
    ): ProductInCart {
        return ProductInCart(
            id = response.productId,
            name = response.productName,
            imageUrl = response.productImageLink,
            prise = response.productPrice,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart,
        )
    }
}