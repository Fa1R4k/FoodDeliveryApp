package com.example.fooddeliveryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.ProductRepository
import com.example.fooddeliveryapp.domain.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<ProductItem>>()
    val liveData: LiveData<List<ProductItem>> get() = _liveData

    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData

    private val _categoryLiveData = MutableLiveData<MutableList<CategoryData>>()
    val categoryLiveData: LiveData<MutableList<CategoryData>> get() = _categoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketTimeoutException -> _errorLiveData.value = R.string.fatal
            else -> _errorLiveData.value = R.string.unknown
        }
    }

    fun getCategory() {
        viewModelScope.launch(exceptionHandler) {
            _categoryLiveData.value = repository.getCategory().toMutableList()
        }
    }

    fun getProduct(category: String) {
            _loadingLiveData.value = true
            viewModelScope.launch(exceptionHandler) {
                _liveData.value = repository.getProductByCategory(category)
                _loadingLiveData.value = false
            }
    }

    fun getProduct() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _liveData.value = repository.getMenu()
            _loadingLiveData.value = false
        }
    }
}