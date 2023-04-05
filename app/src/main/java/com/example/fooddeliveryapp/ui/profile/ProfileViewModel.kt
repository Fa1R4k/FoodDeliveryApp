package com.example.fooddeliveryapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private val _authorizedLiveData = MutableLiveData<Boolean>()
    val authorizedLiveData: LiveData<Boolean> get() = _authorizedLiveData


    fun updateUser() {
        viewModelScope.launch {
            _userLiveData.value = userRepository.getUser()
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