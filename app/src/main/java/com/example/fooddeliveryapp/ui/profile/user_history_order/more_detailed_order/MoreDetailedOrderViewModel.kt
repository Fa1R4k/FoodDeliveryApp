package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MoreDetailedOrderViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userOrderHistoryLiveData = MutableLiveData<List<CartProduct>>()
    val userOrderHistoryLiveData: LiveData<List<CartProduct>> get() = _userOrderHistoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    fun getUserOrder(time: String) {
        viewModelScope.launch {
            _userOrderHistoryLiveData.value =
                userRepository.getUser().orderHistory.find { it.orderTime == time }?.cartProducts
                    ?: emptyList()
        }
    }
}
