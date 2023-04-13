package com.example.fooddeliveryapp.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.model.CategoryData
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<ProductItem>>()
    val liveData: LiveData<List<ProductItem>> get() = _liveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    fun getProduct(searchString: String) {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _liveData.value =
                repository.search(searchString)
            _loadingLiveData.value = false
        }
    }
}