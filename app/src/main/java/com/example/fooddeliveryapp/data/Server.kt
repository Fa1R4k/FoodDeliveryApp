package com.example.fooddeliveryapp.data

import com.example.fooddeliveryapp.domain.CategoryData
import com.example.fooddeliveryapp.data.models.ProductItemResponse
import javax.inject.Inject

class Server @Inject constructor() {

    val category = "Суши"
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
                "",
                "8.7",
                "Леон",
                false,
                "бургер1",
                "Бургеры",
                mapOf("Маленькая" to 17.0, "Средняя" to 23.0, "Большая" to 28.0)),
                ProductItemResponse(7,
                    "https://b1.filmpro.ru/c/87928.jpg",
                    "8.2",
                    "Амадей",
                    true,
                    "тоже не смотрел"),
                ProductItemResponse(8,
                    "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/e0d164bb-68a4-4f16-bc2a-3994b4df36d4/1920x",
                    "8.1",
                    "Ганди",
                    true,
                    "интерестный",
                    "Бургеры"),
                ProductItemResponse(9,
                    "https://b1.filmpro.ru/c/10103.jpg",
                    "8.3",
                    "Американская история X",
                    false,
                    "опять не смотрел",
                    "Бургеры"),
                ProductItemResponse(10,
                    "https://upload.wikimedia.org/wikipedia/ru/thumb/1/16/Slumdog_Millionaire_poster.jpg/203px-Slumdog_Millionaire_poster.jpg",
                    "7.7",
                    "Миллионер из трущоб",
                    true,
                    "очень интересно",
                    "Бургеры")),
            listOf(ProductItemResponse(11,
                "https://b1.filmpro.ru/c/77153.jpg",
                "8.8",
                "Список Шиндлера",
                true,
                "очень грустный",
                "Драма"),
                ProductItemResponse(12,
                    "https://www.kino-teatr.ru/movie/posters/big/1/22041.jpg",
                    "8.9",
                    "Форрест Гамп",
                    true,
                    "поучительный",
                    "Драма"),
                ProductItemResponse(13,
                    "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/226a2544-74f7-4d29-92f2-48c0155c4852/1920x",
                    "8.0",
                    "Город бога",
                    false,
                    "опять же не смотрел",
                    "Драма"),
                ProductItemResponse(14,
                    "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/61c2b142-5a06-444b-a3ed-944a765d7d75/1920x",
                    "7.8",
                    "Большой Лебовски",
                    false,
                    "очень крутой",
                    category),
                ProductItemResponse(15,
                    "https://avatars.mds.yandex.net/get-kinopoisk-image/1704946/24a1df5a-2037-481c-baa4-ed20b104d827/1920x",
                    "8.2",
                    "Лицо со шрамом",
                    false,
                    "классика",
                    category,
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