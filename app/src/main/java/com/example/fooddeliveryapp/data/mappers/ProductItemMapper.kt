package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.ProductItemResponse
import com.example.fooddeliveryapp.domain.model.ProductItem
import javax.inject.Inject

class ProductItemMapper @Inject constructor() {

    operator fun invoke(response: List<List<ProductItemResponse>>): List<ProductItem> =
        response.flatMap { productList ->
            listOf(
                ProductItem.ProductTitleItem(productList.firstOrNull()?.category.orEmpty()),
                *productList.map { productResponse ->
                    productResponse.toProductData()
                }.toTypedArray()
            )
        }

    @JvmName("invoke1")
    operator fun invoke(response: List<ProductItemResponse>): List<ProductItem> =
        response.map { it.toProductData() }

    operator fun invoke(response: ProductItemResponse): ProductItem.ProductData =
        response.toProductData()



    private fun ProductItemResponse.toProductData(): ProductItem.ProductData {
        val id = id ?: 0
        val imageUrl = imageUrl.orEmpty()
        val name = name.orEmpty()
        val description = description.orEmpty()
        val price = price ?: mapOf()
        return ProductItem.ProductData(id,name, description, imageUrl, price)
    }
}
