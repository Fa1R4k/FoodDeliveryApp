package com.example.fooddeliveryapp.data.source

import com.example.fooddeliveryapp.data.database.CartDao
import com.example.fooddeliveryapp.data.database.CartEntity
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val cartDao: CartDao,
) {
    fun getAll(): List<CartEntity> = cartDao.getAll()

    fun insert(product: CartEntity) = cartDao.insert(product)

    fun delete(products: CartEntity) = cartDao.delete(products)
}