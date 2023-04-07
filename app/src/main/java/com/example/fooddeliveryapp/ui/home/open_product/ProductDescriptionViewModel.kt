package com.example.fooddeliveryapp.ui.home.open_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDescriptionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<ProductItem.ProductData>()
    val liveData: LiveData<ProductItem.ProductData> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _parametersLiveData = MutableLiveData<List<String>>()
    val parametersLiveData: LiveData<List<String>> get() = _parametersLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData


    fun getProduct(id: Int) {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _liveData.value = productRepository.getProductById(id)
            _loadingLiveData.value = false
            _parametersLiveData.value = _liveData.value?.price?.keys?.toList()
        }
    }

    fun addToCard(
        productItem: ProductItem.ProductData,
        productPrise: Double,
        productCount: Int,
        productParameter: String,
    ) {
        viewModelScope.launch {
            val product = cartRepository.getProductFromDataBase(productItem.id, productParameter)
            if (product.id == -1) {
                cartRepository.addProductToCart(productItem,
                    productPrise,
                    productCount,
                    productParameter)
            } else {
                val addProductCount = product.countProductInCart + 1
                cartRepository.addProductToCart(productItem,
                    productPrise,
                    addProductCount,
                    productParameter)
            }
        }
    }
}