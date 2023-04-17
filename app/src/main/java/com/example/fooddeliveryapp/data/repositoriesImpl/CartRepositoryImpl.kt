package com.example.fooddeliveryapp.data.repositoriesImpl

import com.example.fooddeliveryapp.data.database.CartEntity
import com.example.fooddeliveryapp.data.mappers.CartEntityMapper
import com.example.fooddeliveryapp.data.mappers.CartProductMapper
import com.example.fooddeliveryapp.data.source.DataBaseSource
import com.example.fooddeliveryapp.domain.repositories.CartRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataBase: DataBaseSource,
    private val cartEntityMapper: CartEntityMapper,
    private val productInCartMapper: CartProductMapper,
) : CartRepository {

    override suspend fun getProductFromDataBase(id: Int, parameter: String): CartProduct =
        withContext(Dispatchers.IO) {
            productInCartMapper(cartDataBase.getAll()
                .firstOrNull{ it.productId == id && it.productParameter == parameter }
                ?: CartEntity(1, "", "", 0.0, "", 1)
            )
        }

    override suspend fun containsProductInDataBase(id: Int, parameter: String): Boolean =
        withContext(Dispatchers.IO) {
            cartDataBase.getAll()
                .firstNotNullOfOrNull { it.productId == id && it.productParameter == parameter } == true
        }

    override suspend fun addProductToCart(
        productItem: ProductItem.ProductData,
        productPrice: Double,
        productCount: Int,
        productParameter: String,
    ) {
        withContext(Dispatchers.IO) {
            cartDataBase.insert(
                cartEntityMapper(
                    productItem,
                    productPrice,
                    productCount,
                    productParameter
                )
            )
        }
    }

    override suspend fun addProductToCart(productItem: List<CartProduct>) {
        withContext(Dispatchers.IO) {
            productItem.map { cartDataBase.insert(cartEntityMapper(it)) }
        }
    }

    override suspend fun getAllProductFromCart(): List<CartProduct> =
        withContext(Dispatchers.IO) {
            (cartDataBase.getAll().map { productInCartMapper(it) }.toList())
        }

    override suspend fun deleteCartsFromDataBase() {
        withContext(Dispatchers.IO) {
            cartDataBase.delete(cartDataBase.getAll())
        }
    }

    override suspend fun deleteCartFromDataBase(productInCart: CartProduct) {
        withContext(Dispatchers.IO) {
            cartDataBase.delete(listOf(cartEntityMapper(productInCart)))
        }
    }

    override suspend fun changeCountForProduct(productInCart: CartProduct) {
        withContext(Dispatchers.IO) {
            cartDataBase.insert(cartEntityMapper(productInCart))
        }
    }

    override suspend fun isCartEmpty(): Boolean =
        withContext(Dispatchers.IO) {
            cartDataBase.getAll().isEmpty()
        }
}