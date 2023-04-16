package com.example.fooddeliveryapp.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.use_case.CreateNewUserUseCase
import com.example.fooddeliveryapp.domain.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val createNewUserUseCase: CreateNewUserUseCase,
) : ViewModel() {

    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> get() = _liveData

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun createNewUser(name: String, phone: String, password: String, date: String) {
        viewModelScope.launch(exceptionHandler) {
            _liveData.value =
                userRepository.createUser(createNewUserUseCase.execute(name, phone, password, date))
        }
    }
}