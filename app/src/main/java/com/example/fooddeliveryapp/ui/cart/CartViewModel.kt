package com.example.fooddeliveryapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.NetworkConnection
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.use_case.CartChangesUseCase
import com.example.fooddeliveryapp.domain.use_case.GetPriceFromCartDataBaseUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val getPriceFromCartDataBase: GetPriceFromCartDataBaseUseCase,
    private val networkConnection: NetworkConnection,
    private val cartChangesUseCase: CartChangesUseCase,
) : ViewModel() {

    private val _cartProductsLiveData = MutableLiveData<List<CartProduct>>()
    val cartProductsLiveData: LiveData<List<CartProduct>> get() = _cartProductsLiveData

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

    private val _productCountLiveData = MutableLiveData<Int>()
    val productCountLiveData: LiveData<Int> get() = _productCountLiveData

    private val _networkStateLiveData = MutableLiveData<Boolean>()
    val networkStateLiveData: LiveData<Boolean> get() = _networkStateLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getUserAuthorizationState() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _authorizedLiveData.value = userRepository.isAuthorized()
            _loadingLiveData.value = false
        }
    }

    fun getCartFromData() {
        viewModelScope.launch(exceptionHandler) {
            _cartProductsLiveData.value =
                cartRepository.getAllProductFromCart().sortedBy { it.hashCode() }
            _productCountLiveData.value =
                cartRepository.getAllProductFromCart().sumOf { it.countProductInCart }
            getCartPrice()
        }
    }

    private fun getCartPrice() {
        viewModelScope.launch(exceptionHandler) {
            _priceLiveData.value =
                getPriceFromCartDataBase.execute(cartRepository.getAllProductFromCart())
        }
    }

    fun changeCart(id: Int, parameter: String, changes: CartChanges) {
        _changeLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            cartChangesUseCase.invoke(_cartProductsLiveData.value, id, parameter, changes)
            getCartPrice()
            _productCountLiveData.value =
                cartRepository.getAllProductFromCart().sumOf { it.countProductInCart }
            _changeLiveData.value = false
        }
    }

    fun isEmptyCart() {
        viewModelScope.launch(exceptionHandler) {
            _isEmptyCartViewModel.value = cartRepository.isCartEmpty()
        }
    }

    fun getNetworkState() {
        networkConnection.isNetworkAvailable().let {
            _networkStateLiveData.value = it
            _loadingLiveData.value = it
        }
    }
}