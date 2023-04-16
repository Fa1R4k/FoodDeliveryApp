package com.example.fooddeliveryapp.ui.profile.user_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.model.User
import com.example.fooddeliveryapp.domain.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getUser() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _userLiveData.value = userRepository.getUser()
            _loadingLiveData.value = false
        }
    }

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData
}