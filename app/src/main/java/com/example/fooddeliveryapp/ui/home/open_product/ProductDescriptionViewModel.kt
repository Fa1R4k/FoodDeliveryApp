package com.example.fooddeliveryapp.ui.home.open_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getProduct(id: Int) {
        viewModelScope.launch {
            _liveData.value = repository.getProductById(id.toString())
        }
    }

    fun addToCard(productItem: ProductItem.ProductData, productCount: Int, productParameter: String) {
        viewModelScope.launch {
            repository.addProductToCart(productItem,productCount, productParameter)
        }
    }
}