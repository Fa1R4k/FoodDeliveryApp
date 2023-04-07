package com.example.fooddeliveryapp.ui.cart.confirm_purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.domain.model.User
import com.example.fooddeliveryapp.domain.use_case.GetCurrentDateUseCase
import com.example.fooddeliveryapp.domain.use_case.GetPriceFromCartDataBaseUseCase
import com.example.fooddeliveryapp.domain.use_case.UpdateDiscountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmPurchaseViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val updateDiscountUseCase: UpdateDiscountUseCase,
    private val getPriceFromCartDataBase: GetPriceFromCartDataBaseUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<List<CartProduct>>()
    val liveData: LiveData<List<CartProduct>> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private fun deleteCart() {
        viewModelScope.launch {
            cartRepository.deleteCartsFromDataBase()
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            val user = userRepository.getUser()
            val cartProducts = cartRepository.getAllProductFromCart()
            user.totalSpend += getPriceFromCartDataBase.execute(cartProducts)
            userRepository.updateUser(updateDiscountUseCase.execute(user), cartProducts, getCurrentDateUseCase.execute())
            deleteCart()
        }
    }

    fun getCartPrice() {
        viewModelScope.launch {
            _priceLiveData.value =
                getPriceFromCartDataBase.execute(cartRepository.getAllProductFromCart())
        }
    }

    fun getUser() {
        viewModelScope.launch {
            _userLiveData.value = userRepository.getUser()
        }
    }

    fun addUserAddress(address: String) {
        viewModelScope.launch {
            val user = userRepository.getUser()
            user.address.add(address)
            _userLiveData.value = user
            userRepository.updateUser(user)
        }
    }
}