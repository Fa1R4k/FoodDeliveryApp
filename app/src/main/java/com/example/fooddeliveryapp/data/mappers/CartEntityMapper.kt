package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.ProductItem
import javax.inject.Inject

class CartEntityMapper @Inject constructor() {

    operator fun invoke(
        response: ProductItem.ProductData,
        productPrice: Double,
        productCount: Int,
        productParameter: String,
    ): CartEntity {
        return CartEntity(
            productId = response.id,
            productName = response.name,
            productImageLink = response.imageUrl,
            productPrice = productPrice,
            productParameter = productParameter,
            countProductInCart = productCount
        )
    }
    operator fun invoke(
        response : CartProduct
    ): CartEntity {
        return CartEntity(
            productId = response.id,
            productName = response.name,
            productImageLink = response.imageUrl,
            productPrice = response.prise,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart
        )
    }
}