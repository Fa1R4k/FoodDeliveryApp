package com.example.fooddeliveryapp.data

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
    private val cartDataBase: DataBaseSource,
    private val productItemMapper: ProductItemMapper,
    private val cartEntityMapper: CartEntityMapper,
    private val productInCartMapper: ProductInCartMapper,
) : Repository {

    override suspend fun getMenu(): List<ProductItem> = withContext(Dispatchers.IO) {
        productItemMapper(server.getMenu())
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

    override suspend fun getProductFromDataBase(id: Int, parameter: String): ProductInCart {
        var productInCart = ProductInCart(-1, "", "", 0.0, "", 0)
        withContext(Dispatchers.IO) {
            try {
                productInCart = productInCartMapper(cartDataBase.getAll()
                    .first { it.productId == id && it.productParameter == parameter })
            } catch (_: Exception) {
            }
        }
        return productInCart
    }

    override suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productPrice: Double,
        productCount: Int,
        productParameter: String,
    ) {
        withContext(Dispatchers.IO) {
            cartDataBase.insert(cartEntityMapper(productItem,
                productPrice,
                productCount,
                productParameter))
        }
    }

    override suspend fun getAllProductFromCart(): List<ProductInCart> =
        withContext(Dispatchers.IO) {
            (cartDataBase.getAll().map { productInCartMapper(it) }.toList())
        }

    override suspend fun getPriceByParameter(id: Int, parameter: String): Double =
        withContext(Dispatchers.IO) {
            server.getPrice(id, parameter)
        }

    override suspend fun deleteCartsFromDataBase() {
        withContext(Dispatchers.IO) {
            cartDataBase.delete(cartDataBase.getAll())
        }
    }

    override suspend fun deleteCartFromDataBase(productInCart: ProductInCart) {
        withContext(Dispatchers.IO) {
            cartDataBase.delete(listOf(cartEntityMapper(productInCart)))
        }
    }

    override suspend fun changeCountForProduct(productInCart: ProductInCart) {
        withContext(Dispatchers.IO) {
            cartDataBase.insert(cartEntityMapper(productInCart))
        }
    }
}