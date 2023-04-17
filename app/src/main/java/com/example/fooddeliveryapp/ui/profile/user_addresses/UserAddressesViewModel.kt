package com.example.fooddeliveryapp.ui.profile.user_addresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserAddressesViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userAddressesLiveData = MutableLiveData<List<String>>()
    val userAddressesLiveData: LiveData<List<String>> get() = _userAddressesLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getUserAddresses() {
        viewModelScope.launch(exceptionHandler) {
            _userAddressesLiveData.value = userRepository.getUser().address
        }
    }

    fun addUserAddress(address: String) {
        viewModelScope.launch(exceptionHandler) {
            val user = userRepository.getUser()
            user.address.add(address)
            _userAddressesLiveData.value = user.address
            userRepository.updateUser(user)
        }
    }

    fun deleteAddress(address: String) {
        viewModelScope.launch(exceptionHandler) {
            val user = userRepository.getUser()
            user.address.remove(address)
            userRepository.updateUser(user)
        }
    }
}