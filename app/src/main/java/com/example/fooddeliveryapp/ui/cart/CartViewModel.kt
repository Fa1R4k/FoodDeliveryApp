package com.example.fooddeliveryapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.UseCase.UpdateDiscountUseCase
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val updateDiscountUseCase: UpdateDiscountUseCase
) : ViewModel() {

    private var price = 0.0

    private val _liveData = MutableLiveData<List<CartProduct>>()
    val liveData: LiveData<List<CartProduct>> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _authorizedLiveData = MutableLiveData<Boolean>()
    val authorizedLiveData: LiveData<Boolean> get() = _authorizedLiveData

    fun getUserAuthorizationState() {
        _authorizedLiveData.value = userRepository.isAuthorized()
    }

    fun getCartFromData() {
        viewModelScope.launch {
            _liveData.value = cartRepository.getAllProductFromCart().sortedBy { it.hashCode() }
            getCartPrice()
        }
    }

    private fun getCartPrice() {
        viewModelScope.launch {
            price = 0.0
            cartRepository.getAllProductFromCart().map { price += it.prise * it.countProductInCart }
            _priceLiveData.value = price
        }
    }

    fun deleteCart() {
        viewModelScope.launch {
            cartRepository.deleteCartsFromDataBase()
        }
    }

    fun changeCart(id: Int, parameter: String, changes: CartChanges) {
        viewModelScope.launch {
            val productInCart =
                _liveData.value?.first { it.id == id && it.productParameter == parameter }
                    ?: CartProduct(0,
                        "",
                        "",
                        1.0,
                        "",
                        1)
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
            getCartPrice()
        }
    }

    fun updateUser(purchasePrice: Double) {
        viewModelScope.launch {
            val user = userRepository.getUser()
            user.totalSpend += purchasePrice
            userRepository.updateUser(updateDiscountUseCase.execute(user))
        }
    }
}