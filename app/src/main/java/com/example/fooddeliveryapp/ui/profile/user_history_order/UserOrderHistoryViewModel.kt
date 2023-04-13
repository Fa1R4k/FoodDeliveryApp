package com.example.fooddeliveryapp.ui.profile.user_history_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserOrderHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userOrderHistoryLiveData = MutableLiveData<List<HistoryOrderData>>()
    val userOrderHistoryLiveData: LiveData<List<HistoryOrderData>> get() = _userOrderHistoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    fun getUserOrderHistory() {
        viewModelScope.launch {
            _userOrderHistoryLiveData.value = userRepository.getUser().orderHistory
        }
    }
}
