package com.example.fooddeliveryapp.ui.profile.user_addresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserAddressesViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userAddressesLiveData = MutableLiveData<List<String>>()
    val userAddressesLiveData: LiveData<List<String>> get() = _userAddressesLiveData


    fun getUserAddresses() {
        viewModelScope.launch {
            _userAddressesLiveData.value = userRepository.getUser().address
        }
    }

    fun addUserAddress(address: String) {
        viewModelScope.launch {
            val user = userRepository.getUser()
            user.address.add(address)
            _userAddressesLiveData.value = user.address
            userRepository.updateUser(user)
        }
    }

    fun deleteAddress(address: String) {
        viewModelScope.launch {
            val user = userRepository.getUser()
            user.address.remove(address)
            userRepository.updateUser(user)
        }
    }
}