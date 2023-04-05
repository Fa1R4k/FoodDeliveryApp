package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.models.ProductItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("product/get-menu")
    fun getMenu(): Call<List<List<ProductItemResponse>>>

    @GET("product/get-categories")
    fun getCategories(): Call<List<String>>

    @GET("product/get-product-by-id")
    fun getProductById(@Query("id") id: Int): Call<ProductItemResponse>

    @GET("product/get-tech-by-category")
    fun getProductByCategory(@Query("category") id: String): Call<List<ProductItemResponse>>
}