package com.example.fooddeliveryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.NetworkConnection
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.ProductRepository
import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.model.ProductItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val networkConnection: NetworkConnection,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<ProductItem>>()
    val liveData: LiveData<List<ProductItem>> get() = _liveData

    private val _categoryLiveData = MutableLiveData<MutableList<CategoryData>>()
    val categoryLiveData: LiveData<MutableList<CategoryData>> get() = _categoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _countProductInCartLiveData = MutableLiveData<Int>()
    val countProductInCartData: LiveData<Int> get() = _countProductInCartLiveData

    private val _networkStateLiveData = MutableLiveData<Boolean>()
    val networkStateLiveData: LiveData<Boolean> get() = _networkStateLiveData

    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ ->}

    fun getCategory() {
        viewModelScope.launch(exceptionHandler) {
            _categoryLiveData.value = productRepository.getCategory().toMutableList()
        }
    }

    fun getCountProductInCart() {
        viewModelScope.launch(exceptionHandler) {
            delay(300)
            _countProductInCartLiveData.value =
                cartRepository.getAllProductFromCart().sumOf { it.countProductInCart }
        }
    }

    fun getProduct() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _liveData.value = productRepository.getMenu()
            _loadingLiveData.value = false
        }
    }

    fun getNetworkState() {
        networkConnection.isNetworkAvailable().let {
            _networkStateLiveData.value = it
            _loadingLiveData.value = it
        }
    }
}