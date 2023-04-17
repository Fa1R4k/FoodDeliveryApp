package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.HistoryOrderResponse
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import javax.inject.Inject

class HistoryOrderDataMapper @Inject constructor(
    private val cartProductMapper: CartProductMapper,
) {

    operator fun invoke(response: HistoryOrderResponse): HistoryOrderData {
        return HistoryOrderData(
            orderTime = response.orderTime.orEmpty(),
            cartProducts = response.cartProductItemResponse?.map { cartProductMapper(it) }?.toList()
                ?: mutableListOf(),
            orderPrice = response.orderPrice ?: 0.0
        )
    }
}