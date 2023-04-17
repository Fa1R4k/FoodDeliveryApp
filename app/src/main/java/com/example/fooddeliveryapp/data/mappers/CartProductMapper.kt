package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.data.models.CartProductItemResponse
import com.example.fooddeliveryapp.domain.model.CartProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor() {
    operator fun invoke(
        cartEntity: CartEntity,
    ): CartProduct = with(cartEntity)
    {
        return CartProduct(
            id = productId,
            name = productName,
            imageUrl = productImageLink,
            prise = productPrice,
            productParameter = productParameter,
            countProductInCart = countProductInCart,
        )
    }

    operator fun invoke(
        response: CartProductItemResponse,
    ): CartProduct = with(response) {
        return CartProduct(
            id = id ?: idIsNull,
            name = name.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            prise = prise ?: priseIsNull,
            productParameter = productParameter.orEmpty(),
            countProductInCart = countProductInCart ?: countProductInCartIsNull,
        )
    }
    companion object{
        private const val idIsNull = -1
        private const val priseIsNull = 0.0
        private const val countProductInCartIsNull = 0
    }
}