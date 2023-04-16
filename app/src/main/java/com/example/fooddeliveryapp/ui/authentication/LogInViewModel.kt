package com.example.fooddeliveryapp.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.use_case.HashingPasswordUseCase
import com.example.fooddeliveryapp.domain.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val hashingPasswordUseCase: HashingPasswordUseCase,
) : ViewModel() {

    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun login(phone: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            _liveData.value =
                userRepository.loginUser(phone, hashingPasswordUseCase.execute(password))
        }
    }
}