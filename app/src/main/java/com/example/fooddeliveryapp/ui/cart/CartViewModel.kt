package com.example.fooddeliveryapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private var price = 0.0

    private val _liveData = MutableLiveData<List<ProductInCart>>()
    val liveData: LiveData<List<ProductInCart>> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    fun getCartFromData() {
        viewModelScope.launch {
            _liveData.value = repository.getAllProductFromCart().sortedBy { it.hashCode() }
            getCartPrice()
        }
    }

    private fun getCartPrice() {
        viewModelScope.launch {
            price = 0.0
            repository.getAllProductFromCart().map { price += it.prise * it.countProductInCart }
            _priceLiveData.value = price
        }
    }

    fun deleteCart() {
        viewModelScope.launch {
            repository.deleteCartsFromDataBase()
        }
    }

    fun changeCart(id: Int, parameter: String, changes: CART_CHANGES) {
        viewModelScope.launch {
            val productInCart =
                _liveData.value?.first { it.id == id && it.productParameter == parameter }
                    ?: ProductInCart(0,
                        "",
                        "",
                        1.0,
                        "",
                        1)
            when (changes) {
                CART_CHANGES.ADD -> {
                    productInCart.countProductInCart++
                    repository.changeCountForProduct(productInCart)
                }
                CART_CHANGES.REMOVE -> {
                    productInCart.countProductInCart--
                    repository.changeCountForProduct(productInCart)
                }
                CART_CHANGES.DELETE -> repository.deleteCartFromDataBase(productInCart)
            }
            getCartPrice()
        }
    }
}
