package com.example.fooddeliveryapp.data.repositoriesImpl

import com.example.fooddeliveryapp.data.mappers.CategoryDataMapper
import com.example.fooddeliveryapp.data.mappers.ProductItemMapper
import com.example.fooddeliveryapp.data.network.ProductService
import com.example.fooddeliveryapp.domain.repositories.ProductRepository
import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productItemMapper: ProductItemMapper,
    private val categoryDataMapper: CategoryDataMapper,
    private val productService: ProductService,
) : ProductRepository {

    override suspend fun getMenu(): List<ProductItem> = withContext(Dispatchers.IO) {
        productItemMapper(productService.getMenu())
    }

    override suspend fun getCategory(): List<CategoryData> = withContext(Dispatchers.IO) {
        productService.getCategories().map { categoryDataMapper(it) }
    }

    override suspend fun getProductById(id: Int): ProductItem.ProductData =
        withContext(Dispatchers.IO) {
            productItemMapper(productService.getProductById(id))
        }

    override suspend fun search(query: String): List<ProductItem.ProductData> =
        withContext(Dispatchers.IO) {
            productService.search(query).map { productItemMapper(it) }
        }
}