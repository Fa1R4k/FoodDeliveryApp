package com.example.fooddeliveryapp.domain

import com.example.fooddeliveryapp.data.database.CartEntity

interface Repository {

    suspend fun getMenu(): List<ProductItem>

    suspend fun getUser(): List<ProductItem.ProductData>

    suspend fun getCategory(): MutableList<CategoryData>

    suspend fun getProductByCategory(category: String): List<ProductItem>

    suspend fun getProductById(id: String): ProductItem.ProductData


    suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productCount: Int,
        productParameter: String
    )

    suspend fun getAllProductFromCart(): List<ProductInCart>
}