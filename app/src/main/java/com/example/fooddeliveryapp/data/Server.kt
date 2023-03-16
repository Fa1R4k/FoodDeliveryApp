package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.domain.CategoryData
import com.example.fooddeliveryapp.data.models.ProductItemResponse
import javax.inject.Inject

class Server @Inject constructor() {

    fun getMenu(): List<List<ProductItemResponse>> {
        return listOf(listOf(ProductItemResponse(1,
            "https://dodopizza-a.akamaihd.net/static/Img/Products/70834e6311c0483493bf2279dbc1718d_760x760.webp",
            "9.1",
            "Пепперони",
            false,
            "Вкусная пицца с пепперони",
            "Пицца",
            mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
            ProductItemResponse(2,
                "https://dodopizza-a.akamaihd.net/static/Img/Products/ee610848581545c298a429c05802f56d_366x366.webp",
                "8.4",
                "Сырная",
                true,
                "Вкусная пицца с сыром",
                "Пицца",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
            ProductItemResponse(3,
                "https://dodopizza-a.akamaihd.net/static/Img/Products/f05b3d7ed33647a985d383d68a94bf09_366x366.webp",
                "8.1",
                "Пицца с чем-то",
                true,
                "Вкусная пицца с чем-то",
                "Пицца",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
            ProductItemResponse(4,
                "https://dodopizza-a.akamaihd.net/static/Img/Products/bdc5caa51bd64af1b8712fc03aeaf386_760x760.webp",
                "7.8",
                "Ветчина и сыр",
                true,
                "Вкусная пицца Ветчина и сырВетВкусная пицца Ветчина и сырВетчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр\",\n" + "                    Вкусная пицца Ветчина и сырВетчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр\",\n" + "                    Вкусная пицца Ветчина и сырВетчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр\",\n" + "                    Вкусная пицца Ветчина и сырВетчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр\",\n" + "                    чина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр Ветчина и сыр",
                "Пицца",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
            ProductItemResponse(5,
                "https://dodopizza-a.akamaihd.net/static/Img/Products/a10ad669c5054be2b019613e5cfd2477_760x760.webp",
                "8.3",
                "Маргарита",
                false,
                "Вкусная пицца Маргарита",
                "Пицца",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0))),
            listOf(ProductItemResponse(6,
                "https://i.imgur.com/J5hPBxD.png",
                "8.7",
                "Гамбургер",
                false,
                "вкусный бургер",
                "Бургеры",
                mapOf("price" to 3.0)),
                ProductItemResponse(7,
                    "https://i.imgur.com/nttVsa8.png",
                    "8.2",
                    "Чизбургер",
                    true,
                    "вкусный бургер с сыром",
                    "Бургеры",
                    mapOf("price" to 4.0)),
                ProductItemResponse(8,
                    "",
                    "8.1",
                    "БигБургер",
                    true,
                    "интерестный",
                    "Бургеры",
                    mapOf("price" to 4.0)),
                ProductItemResponse(9,
                    "https://i.imgur.com/nttVsa8.png",
                    "8.3",
                    "БигБургер лайт",
                    false,
                    "опять не смотрел",
                    "Бургеры",
                    mapOf("price" to 5.0)),
                ProductItemResponse(10,
                    "",
                    "7.7",
                    "БигБургер с сыром",
                    true,
                    "очень интересно",
                    "Бургеры",
                    mapOf("price" to 6.0))),
            listOf(ProductItemResponse(11,
                "",
                "8.8",
                "Аризона",
                true,
                "",
                "Суши",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
                ProductItemResponse(12,
                    "",
                    "8.9",
                    "Филадельфия",
                    true,
                    "",
                    "Суши",
                    mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
                ProductItemResponse(13,
                    "",
                    "8.0",
                    "",
                    false,
                    "Калифорния",
                    "Суши",
                    mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
                ProductItemResponse(14,
                    "",
                    "7.8",
                    "Кола",
                    false,
                    "кола как кола",
                    "Напитки",
                    mapOf("4 шт" to 17.0, "8 шт" to 23.0, "16 шт" to 28.0)),
                ProductItemResponse(15,
                    "",
                    "8.2",
                    "Спрайт",
                    false,
                    "спрайтик",
                    "Напитки",
                    mapOf("4 шт" to 17.0, "8 шт" to 23.0, "16 шт" to 28.0))))
    }

    fun getCategory(): MutableList<CategoryData> = mutableListOf(
        CategoryData("Все", false),
        CategoryData("Пицца", false),
        CategoryData("Бургеры", false),
        CategoryData("Суши", false),
        CategoryData("Закуски", false),
        CategoryData("Напитки", false),
        CategoryData("Соусы", false),
    )

    fun getProductByCategory(category: String): List<ProductItemResponse> {
        val firstList = getMenu()
        val list = mutableListOf<ProductItemResponse>()
        for (i in firstList) {
            for (j in i) {
                if (j.category == category) {
                    list.add(j)
                }
            }
        }
        return list
    }

    fun getProductById(id: String): ProductItemResponse {
        val firstList = getMenu()
        for (i in firstList) {
            for (j in i) {
                if (j.id == id.toInt()) {
                    return j
                }
            }
        }
        return ProductItemResponse()
    }

    fun getPrice(id: Int, parameter: String): Double {
        val productItem = getProductById(id.toString())

        return productItem.prise!![parameter]!!
    }
}