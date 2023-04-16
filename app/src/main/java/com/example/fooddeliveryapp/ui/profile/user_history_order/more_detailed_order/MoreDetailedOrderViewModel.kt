package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
class MoreDetailedOrderViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _userOrderHistoryLiveData = MutableLiveData<List<CartProduct>>()
    val userOrderHistoryLiveData: LiveData<List<CartProduct>> get() = _userOrderHistoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _orderRescheduledLiveData = MutableLiveData<Boolean>()
    val orderRescheduledLiveData: LiveData<Boolean> get() = _orderRescheduledLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getUserOrder(time: String) {
        viewModelScope.launch(exceptionHandler) {
            _userOrderHistoryLiveData.value =
                userRepository.getUser().orderHistory.find { it.orderTime == time }?.cartProducts
                    ?: emptyList()
        }
    }

    fun addOrderToCart(list: List<CartProduct>) {
        viewModelScope.launch(exceptionHandler) {
            cartRepository.deleteCartsFromDataBase()
            cartRepository.addProductToCart(list)
            _orderRescheduledLiveData.value = true
        }
    }
}
