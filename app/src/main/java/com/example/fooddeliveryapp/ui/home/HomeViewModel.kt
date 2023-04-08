package com.example.fooddeliveryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.ProductRepository
import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<ProductItem>>()
    val liveData: LiveData<List<ProductItem>> get() = _liveData

    private val _categoryLiveData = MutableLiveData<MutableList<CategoryData>>()
    val categoryLiveData: LiveData<MutableList<CategoryData>> get() = _categoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _countProductInCartLiveData = MutableLiveData<Int>()
    val countProductInCartData: LiveData<Int> get() = _countProductInCartLiveData


    /*private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketTimeoutException -> _errorLiveData.value = R.string.fatal
            else -> _errorLiveData.value = R.string.unknown
        }
    }*/

    fun getCategory() {
        viewModelScope.launch {
            _categoryLiveData.value = productRepository.getCategory().toMutableList()
        }
    }

    fun getCountProductInCart() {
        viewModelScope.launch {
            delay(300)
            _countProductInCartLiveData.value =
                cartRepository.getAllProductFromCart().sumOf { it.countProductInCart }
        }
    }

    fun getProduct(category: String) {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _liveData.value = productRepository.getProductByCategory(category)
            _loadingLiveData.value = false
        }
    }

    fun getProduct() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _liveData.value = productRepository.getMenu()
            _loadingLiveData.value = false
        }
    }
}