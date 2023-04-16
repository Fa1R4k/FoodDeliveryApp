package com.example.fooddeliveryapp.ui.profile

import androidx.lifecycle.*
import com.example.fooddeliveryapp.NetworkConnection
import com.example.fooddeliveryapp.domain.UserRepository
import com.example.fooddeliveryapp.domain.model.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val networkConnection: NetworkConnection,
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    private val _loadingAuthorizedLiveData  = MutableLiveData<Boolean>()
    val loadingAuthorizedLiveData: LiveData<Boolean> get() = _loadingAuthorizedLiveData

    private val _authorizedLiveData = MutableLiveData<Boolean>()
    val authorizedLiveData: LiveData<Boolean> get() = _authorizedLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _networkStateLiveData = MutableLiveData<Boolean>()
    val networkStateLiveData: LiveData<Boolean> get() = _networkStateLiveData

    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ -> _loadingLiveData.value = false }


    fun getUser() {
        viewModelScope.launch(exceptionHandler) {
            _userLiveData.value = userRepository.getUser()
            _loadingLiveData.value = false
        }
    }

    fun logout() {
        userRepository.logout()
    }

    fun getUserAuthorizationState() {
        _loadingLiveData.value = true
        _loadingAuthorizedLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            _authorizedLiveData.value = userRepository.isAuthorized()
            _loadingAuthorizedLiveData.value = false
        }
    }

    fun getNetworkState() {
        _networkStateLiveData.value = networkConnection.isNetworkAvailable()
    }
}