package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.models.ProductItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("product/get-menu")
    suspend fun getMenu(): List<List<ProductItemResponse>>

    @GET("product/get-categories")
    suspend fun getCategories(): List<String>

    @GET("product/get-product-by-id")
    suspend fun getProductById(@Query("id") id: Int): ProductItemResponse

    @GET("product/get-product-by-category")
    suspend fun getProductByCategory(@Query("category") id: String): List<ProductItemResponse>
}