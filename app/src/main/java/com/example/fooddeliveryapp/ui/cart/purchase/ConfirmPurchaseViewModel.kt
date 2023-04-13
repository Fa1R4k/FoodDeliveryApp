package com.example.fooddeliveryapp.ui.cart.purchase

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
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmPurchaseViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val updateDiscountUseCase: UpdateDiscountUseCase,
    private val getPriceFromCartDataBase: GetPriceFromCartDataBaseUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<CartProduct>>()
    val liveData: LiveData<List<CartProduct>> get() = _liveData

    private val _priceLiveData = MutableLiveData<Double>()
    val priceLiveData: LiveData<Double> get() = _priceLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _isUserUpdateLiveData = MutableLiveData<Boolean>()
    val isUserUpdate: LiveData<Boolean> get() = _isUserUpdateLiveData

    private fun deleteCart() {
        viewModelScope.launch {
            cartRepository.deleteCartsFromDataBase()
        }
    }

    fun updateUser() {
        _isUserUpdateLiveData.value = true
        viewModelScope.launch {
            val user = userRepository.getUser()
            val cartProducts = cartRepository.getAllProductFromCart()
            val price = getPriceFromCartDataBase.execute(cartProducts)
            val orderPrise = price - (price * user.discount / 100)
            user.totalSpend += orderPrise
            println(user.totalSpend)
            userRepository.updateUser(
                updateDiscountUseCase.execute(user),
                cartProducts,
                getCurrentDateUseCase.execute(),
                orderPrise
            )
            deleteCart()
            _isUserUpdateLiveData.value = false
        }
    }

    fun getCartPrice() {
        viewModelScope.launch {
            val user = userRepository.getUser().discount
            val price = getPriceFromCartDataBase.execute(cartRepository.getAllProductFromCart())
            _priceLiveData.value = price - (price * user / 100)
        }
    }

    fun getUser() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _userLiveData.value = userRepository.getUser()
            _loadingLiveData.value = false
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