package com.example.fooddeliveryapp.data.mappers

import com.example.fooddeliveryapp.domain.model.CategoryData
import javax.inject.Inject

class CategoryDataMapper @Inject constructor() {
    operator fun invoke(response: String): CategoryData {
        return CategoryData(response,false)
    }
}