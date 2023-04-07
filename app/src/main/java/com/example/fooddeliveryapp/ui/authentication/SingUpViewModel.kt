package com.example.fooddeliveryapp.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.use_case.CreateNewUserUseCase
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val createNewUserUseCase: CreateNewUserUseCase,
) : ViewModel() {

    private val _liveData = MutableLiveData<Boolean>()
    val liveData: LiveData<Boolean> get() = _liveData

    fun createNewUser(name: String, phone: String, password: String, date: String) {
        viewModelScope.launch {
            _liveData.value = userRepository.createUser(createNewUserUseCase.execute(name, phone, password, date))
        }
    }
}