package com.example.fooddeliveryapp.domain.repositories

import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.ProductItem

interface CartRepository {

    suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productPrice: Double,
        productCount: Int,
        productParameter: String,
    )

    suspend fun getAllProductFromCart(): List<CartProduct>
    suspend fun deleteCartsFromDataBase()
    suspend fun changeCountForProduct(productInCart: CartProduct)
    suspend fun deleteCartFromDataBase(productInCart: CartProduct)
    suspend fun getProductFromDataBase(id: Int, parameter: String): CartProduct
    suspend fun isCartEmpty(): Boolean
    suspend fun addProductToCart(productItem: List<CartProduct>)
    suspend fun containsProductInDataBase(id: Int, parameter: String): Boolean
}