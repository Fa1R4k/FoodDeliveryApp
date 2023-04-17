package com.example.fooddeliveryapp.domain.use_case

import com.example.fooddeliveryapp.domain.repositories.CartRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.ui.cart.CartChanges
import javax.inject.Inject

class CartChangesUseCase @Inject constructor(
    private val cartRepository: CartRepository,
) {

    suspend fun invoke(
        listCartProduct: List<CartProduct>?,
        id: Int,
        parameter: String,
        changes: CartChanges,
    ) {
        val productInCart =
            listCartProduct?.first { it.id == id && it.productParameter == parameter }
                ?: DEFAULT_PRODUCT
        when (changes) {
            CartChanges.ADD -> {
                productInCart.countProductInCart++
                cartRepository.changeCountForProduct(productInCart)
            }
            CartChanges.REMOVE -> {
                productInCart.countProductInCart--
                cartRepository.changeCountForProduct(productInCart)
            }
            CartChanges.DELETE -> cartRepository.deleteCartFromDataBase(productInCart)
        }
    }

    companion object {
        private val DEFAULT_PRODUCT = CartProduct(
            0,
            "",
            "",
            1.0,
            "",
            1
        )
    }
}