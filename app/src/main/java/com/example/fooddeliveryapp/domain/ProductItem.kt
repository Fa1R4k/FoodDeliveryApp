package com.example.fooddeliveryapp.domain

sealed class ProductItem {
    data class ProductData(
        val id: Int,
        val name: String,
        val description: String,
        val imageUrl: String,
        var prise: Map<String,Double>,
    ) : ProductItem()

    data class ProductTitleItem(val title: String) : ProductItem()
}
