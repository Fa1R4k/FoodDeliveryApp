package com.example.fooddeliveryapp.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.use_case.HashingPasswordUseCase
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val hashingPasswordUseCase: HashingPasswordUseCase,
) : ViewModel() {
    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> get() = _liveData


    fun login(phone: String, password: String) {
        viewModelScope.launch {
            _liveData.value =
                userRepository.loginUser(phone, hashingPasswordUseCase.execute(password))
        }
    }
}