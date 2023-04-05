package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.data.models.CartProductItemResponse
import com.example.fooddeliveryapp.domain.model.CartProduct
import javax.inject.Inject

class CartProductMapper @Inject constructor() {
    operator fun invoke(
        response: CartEntity,
    ): CartProduct {
        return CartProduct(
            id = response.productId,
            name = response.productName,
            imageUrl = response.productImageLink,
            prise = response.productPrice,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart,
        )
    }

    operator fun invoke(
        response: CartProductItemResponse,
    ): CartProduct {
        return CartProduct(
            id = response.id,
            name = response.name,
            imageUrl = response.imageUrl,
            prise = response.prise,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart,
        )
    }
}