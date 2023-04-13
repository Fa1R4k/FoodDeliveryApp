package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.CartProductItemResponse
import com.example.fooddeliveryapp.domain.model.CartProduct
import javax.inject.Inject

class CartProductItemResponseMapper @Inject constructor() {
    operator fun invoke(response: CartProduct): CartProductItemResponse {
        return CartProductItemResponse(
            id = response.id,
            name = response.name,
            imageUrl = response.imageUrl,
            prise = response.prise,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart,
        )
    }
}