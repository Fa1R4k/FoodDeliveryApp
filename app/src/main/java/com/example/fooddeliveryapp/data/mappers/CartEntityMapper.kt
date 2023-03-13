package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.ProductItem
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
            countProductInCart = productCount,
            user = 1
        )
    }
    operator fun invoke(
        response : ProductInCart
    ): CartEntity {
        return CartEntity(
            productId = response.id,
            productName = response.name,
            productImageLink = response.imageUrl,
            productPrice = response.prise,
            productParameter = response.productParameter,
            countProductInCart = response.countProductInCart,
            user = 1
        )
    }
}