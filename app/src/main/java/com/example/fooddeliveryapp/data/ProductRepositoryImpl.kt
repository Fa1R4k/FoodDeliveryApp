package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.mappers.CategoryDataMapper
import com.example.fooddeliveryapp.data.mappers.ProductItemMapper
import com.example.fooddeliveryapp.domain.ProductRepository
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
        productItemMapper(productService.getMenu().execute().body() ?: throw Exception())
    }

    override suspend fun getCategory(): List<CategoryData> = withContext(Dispatchers.IO) {
        productService.getCategories().execute().body()?.map { categoryDataMapper(it) }
            ?: throw Exception()
    }

    override suspend fun getProductByCategory(category: String): List<ProductItem> =
        withContext(Dispatchers.IO) {
            if (productService.getCategories().execute().body()?.contains(category)
                    ?: throw Exception()
            ) {
                productService.getProductByCategory(category).execute().body()
                    ?.map { productItemMapper(it) }
                    ?: throw Exception()
            } else {
                getMenu()
            }
        }

    override suspend fun getProductById(id: Int): ProductItem.ProductData =
        withContext(Dispatchers.IO) {
            productItemMapper(productService.getProductById(id).execute().body()
                ?: throw Exception())
        }
}