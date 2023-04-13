package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.data.models.HistoryOrderResponse
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import javax.inject.Inject

class HistoryOrderResponseMapper @Inject constructor(
    private val cartProductItemResponseMapper:CartProductItemResponseMapper
) {
    operator fun invoke(historyOrderData: HistoryOrderData): HistoryOrderResponse{
        return HistoryOrderResponse(
            orderTime = historyOrderData.orderTime,
            orderPrice = historyOrderData.orderPrice,
            cartProductItemResponse = historyOrderData.cartProducts.map { cartProductItemResponseMapper(it) }
            )
    }
}