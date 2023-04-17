package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.CartProductItemResponse
import com.example.fooddeliveryapp.domain.model.CartProduct
import javax.inject.Inject

class CartProductItemResponseMapper @Inject constructor() {
    operator fun invoke(response: CartProduct): CartProductItemResponse = with(response) {
        CartProductItemResponse(
            id = id,
            name = name,
            imageUrl = imageUrl,
            prise = prise,
            productParameter = productParameter,
            countProductInCart = countProductInCart
        )
    }
}