package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.mappers.ProductItemMapper
import com.example.fooddeliveryapp.domain.ProductItem
import kotlinx.coroutines.delay
import javax.inject.Inject

class User @Inject constructor(
    private val mapper: ProductItemMapper,
    private val server: Server
) {

    fun checkFilm(id: Int): Boolean {
        for (i in listFavoriteFilm) {
            if (i.id == id) {
                return true
            }
        }
        return false
    }



    suspend fun getUser(): List<ProductItem.ProductData> {
        delay(3000)
        return listFavoriteFilm
    }

    companion object {
        private val listFavoriteFilm = mutableListOf<ProductItem.ProductData>()
    }
}