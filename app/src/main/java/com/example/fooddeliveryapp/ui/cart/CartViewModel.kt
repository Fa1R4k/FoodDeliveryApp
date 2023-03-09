package com.example.fooddeliveryapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.ProductItem
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
            _liveData.value = repository.getAllProductFromCart()
            getCartPrice()
        }
    }

    private fun getCartPrice() {
        price = 0.0
        _liveData.value?.map { price += it.prise }
        _priceLiveData.value = price
    }
}
