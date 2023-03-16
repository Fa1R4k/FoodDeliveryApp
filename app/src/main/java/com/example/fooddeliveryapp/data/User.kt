package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.mappers.ProductItemMapper
import javax.inject.Inject

class User @Inject constructor(
    private val mapper: ProductItemMapper,
    private val server: Server,
) {

}