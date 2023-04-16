package com.example.fooddeliveryapp.ui.home.open_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.domain.ProductRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDescriptionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<ProductItem.ProductData>()
    val liveData: LiveData<ProductItem.ProductData> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _parametersLiveData = MutableLiveData<List<String>>()
    val parametersLiveData: LiveData<List<String>> get() = _parametersLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getProduct(id: Int) {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _liveData.value = productRepository.getProductById(id)
            _loadingLiveData.value = false
            _parametersLiveData.value = _liveData.value?.price?.keys?.toList()
        }
    }

    fun addToCard(productItem: ProductItem.ProductData, prise: Double, parameter: String) {
        viewModelScope.launch(exceptionHandler) {
            val productExistsInCart =
                cartRepository.containsProductInDataBase(productItem.id, parameter)

            val countForFirstElemInCart = 1
            val addElementInCard = 1

            val addProductCount = if (productExistsInCart) {
                cartRepository.getProductFromDataBase(
                    productItem.id, parameter
                ).countProductInCart + addElementInCard
            } else {
                countForFirstElemInCart
            }
            cartRepository.addProductToCart(
                productItem, prise, addProductCount, parameter
            )
        }
    }
}