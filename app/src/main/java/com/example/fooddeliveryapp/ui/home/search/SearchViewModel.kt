package com.example.fooddeliveryapp.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.domain.repositories.ProductRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<ProductItem>>()
    val liveData: LiveData<List<ProductItem>> get() = _liveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getProduct(searchString: String) {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _liveData.value =
                repository.search(searchString)
            _loadingLiveData.value = false
        }
    }
}