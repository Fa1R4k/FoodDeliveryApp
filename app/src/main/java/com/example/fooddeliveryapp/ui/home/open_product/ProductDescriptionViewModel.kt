package com.example.fooddeliveryapp.ui.home.open_product

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductItem
import com.example.fooddeliveryapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDescriptionViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _liveData = MutableLiveData<ProductItem.ProductData>()
    val liveData: LiveData<ProductItem.ProductData> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _firstPriceLiveData = MutableLiveData<Double>()
    val firstPriceLiveData: LiveData<Double> get() = _firstPriceLiveData


    private val _parametersLiveData = MutableLiveData<List<String>>()
    val parametersLiveData: LiveData<List<String>> get() = _parametersLiveData

    fun getProduct(id: Int) {
        viewModelScope.launch {
            _liveData.value = repository.getProductById(id.toString())
            _firstPriceLiveData.value = _liveData.value?.prise?.values?.first() ?: 0.0
            _parametersLiveData.value = _liveData.value?.prise?.keys?.toList()
        }
    }

    fun addToCard(
        productItem: ProductItem.ProductData,
        productPrise: Double,
        productCount: Int,
        productParameter: String,
    ) {
        viewModelScope.launch {
            val product = repository.getProductFromDataBase(productItem.id, productParameter)
            if (product.id == -1) {
                repository.addProductToCart(productItem,
                    productPrise,
                    productCount,
                    productParameter)
            } else {
                val addProductCount = product.countProductInCart + 1
                repository.addProductToCart(productItem,
                    productPrise,
                    addProductCount,
                    productParameter)
            }
        }
    }

    fun getPriceByParameter(id: Int, parameter: String) {
        viewModelScope.launch {
            _priceLiveData.value = repository.getPriceByParameter(id, parameter)
        }
    }
}