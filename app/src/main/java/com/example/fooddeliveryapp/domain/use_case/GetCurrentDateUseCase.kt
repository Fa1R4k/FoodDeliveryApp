package com.example.fooddeliveryapp.domain.use_case

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCurrentDateUseCase @Inject constructor() {
    fun execute(): String {
        val dateReg = Date()
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.format(dateReg)
    }
}