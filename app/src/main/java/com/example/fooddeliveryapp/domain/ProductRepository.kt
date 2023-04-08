package com.example.fooddeliveryapp.domain

import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.model.ProductItem

interface ProductRepository {

    suspend fun getMenu(): List<ProductItem>
    suspend fun getCategory(): List<CategoryData>
    suspend fun getProductById(id: Int): ProductItem.ProductData
}