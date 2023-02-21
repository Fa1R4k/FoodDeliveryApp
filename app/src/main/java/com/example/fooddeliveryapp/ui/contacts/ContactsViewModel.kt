package com.example.fooddeliveryapp.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(

) : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> get() = _liveData


}