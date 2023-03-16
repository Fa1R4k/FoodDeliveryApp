package com.example.fooddeliveryapp.domain

interface Repository {

    suspend fun getMenu(): List<ProductItem>
    suspend fun getCategory(): MutableList<CategoryData>
    suspend fun getProductByCategory(category: String): List<ProductItem>
    suspend fun getProductById(id: String): ProductItem.ProductData
    suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productPrice: Double,
        productCount: Int,
        productParameter: String,
    )

    suspend fun getAllProductFromCart(): List<ProductInCart>
    suspend fun getPriceByParameter(id: Int, parameter: String): Double
    suspend fun deleteCartsFromDataBase()
    suspend fun changeCountForProduct(productInCart: ProductInCart)
    suspend fun deleteCartFromDataBase(productInCart: ProductInCart)
    suspend fun getProductFromDataBase(id: Int, parameter: String): ProductInCart
}