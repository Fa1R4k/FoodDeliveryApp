package com.example.fooddeliveryapp.domain.model

sealed class ProductItem {
    data class ProductData(
        val id: Int,
        val name: String,
        val description: String,
        val imageUrl: String,
        var price: Map<String,Double>,
        var category: String,
    ) : ProductItem()

    data class ProductTitleItem(val title: String) : ProductItem()
}
