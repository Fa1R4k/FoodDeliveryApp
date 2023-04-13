package com.example.fooddeliveryapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private val _authorizedLiveData = MutableLiveData<Boolean>()
    val authorizedLiveData: LiveData<Boolean> get() = _authorizedLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData


    fun getUser() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            _userLiveData.value = userRepository.getUser()
            _loadingLiveData.value = false
        }
    }

    fun logout() {
        userRepository.logout()
    }

    fun getUserAuthorizationState() {
        _authorizedLiveData.value = userRepository.isAuthorized()
    }

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData
}