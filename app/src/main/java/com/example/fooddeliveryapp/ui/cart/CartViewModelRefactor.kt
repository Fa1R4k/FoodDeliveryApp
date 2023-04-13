package com.example.fooddeliveryapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.use_case.GetPriceFromCartDataBaseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModelRefactor @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val getPriceFromCartDataBase: GetPriceFromCartDataBaseUseCase,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<CartProduct>>()
    val liveData: LiveData<List<CartProduct>> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _authorizedLiveData = MutableLiveData<Boolean>()
    val authorizedLiveData: LiveData<Boolean> get() = _authorizedLiveData

    private val _isEmptyCartViewModel = MutableLiveData<Boolean>()
    val isEmptyCartViewModel: LiveData<Boolean> get() = _isEmptyCartViewModel

    private val _changeLiveData = MutableLiveData<Boolean>()
    val changeLiveData: LiveData<Boolean> get() = _changeLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    fun getUserAuthorizationState() {
        _authorizedLiveData.value = userRepository.isAuthorized()
    }

    fun getCartFromData() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _liveData.value = cartRepository.getAllProductFromCart().sortedBy { it.hashCode() }
            getCartPrice()
            _loadingLiveData.value = false
        }
    }

    private fun getCartPrice() {
        viewModelScope.launch {
            _priceLiveData.value =
                getPriceFromCartDataBase.execute(cartRepository.getAllProductFromCart())
        }
    }

    fun changeCart(id: Int, parameter: String, changes: CartChanges) {
        _changeLiveData.value = true
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
            _changeLiveData.value = false
        }
    }

    fun isEmptyCart() {
        viewModelScope.launch {
            _isEmptyCartViewModel.value = cartRepository.isCartEmpty()
        }
    }
}