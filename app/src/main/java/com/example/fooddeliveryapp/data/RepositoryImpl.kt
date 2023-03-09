package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.data.mappers.CartEntityMapper
import com.example.fooddeliveryapp.data.mappers.ProductInCartMapper
import com.example.fooddeliveryapp.domain.CategoryData
import com.example.fooddeliveryapp.data.mappers.ProductItemMapper
import com.example.fooddeliveryapp.data.source.DataBaseSource
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.Repository
import com.example.fooddeliveryapp.domain.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val server: Server,
    private val user: User,
    private val cartDataBase: DataBaseSource,
    private val productItemMapper: ProductItemMapper,
    private val cartEntityMapper: CartEntityMapper,
    private val productInCartMapper: ProductInCartMapper,
) : Repository {

    override suspend fun getMenu(): List<ProductItem> = withContext(Dispatchers.IO) {
        productItemMapper(server.getMenu())
    }


    override suspend fun getUser(): List<ProductItem.ProductData> {
        return user.getUser()
    }

    override suspend fun getCategory(): MutableList<CategoryData> {
        return server.getCategory()
    }

    override suspend fun getProductByCategory(category: String): List<ProductItem> =
        withContext(Dispatchers.IO) {
            productItemMapper(server.getProductByCategory(category))
        }

    override suspend fun getProductById(id: String): ProductItem.ProductData =
        withContext(Dispatchers.IO) {
            productItemMapper(server.getProductById(id))
        }

    override suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productCount: Int,
        productParameter: String,
    ) {
        withContext(Dispatchers.IO) {
            cartDataBase.insert(cartEntityMapper(productItem, 1.0, productCount, productParameter))
        }
    }

    override suspend fun getAllProductFromCart(): List<ProductInCart> =
        withContext(Dispatchers.IO) {
            (cartDataBase.getAll().map { productInCartMapper(it) }.toList())
        }
}