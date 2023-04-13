package com.example.fooddeliveryapp.domain.model

data class User(
    val name: String,
    val number: String,
    val hashPassword: String,
    val date: String,
    val dateRegistration: String,
    var totalSpend: Double = 0.0,
    val orderHistory: MutableList<HistoryOrderData> = mutableListOf(),
    var orderCount: Int = 0,
    val address: MutableList<String> = mutableListOf(),
    var nextDiscountSum: Double = DISCOUNT_FIRST_PERCENT,
    var discount: Int = 0,
    var id: String = "",
) {
    init {
        if (id.isEmpty()) {
            id = this.hashCode().toString()
        }
    }

    companion object {
        const val DISCOUNT_FIRST_PERCENT = 50.0
    }
}

