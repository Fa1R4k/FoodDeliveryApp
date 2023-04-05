package com.example.fooddeliveryapp.ui.profile.user_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.model.User
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    init {

    }

    fun getUser() {
        viewModelScope.launch {
            _userLiveData.value = userRepository.getUser()
        }
    }

    fun logout() {
        userRepository.logout()
    }

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData


}