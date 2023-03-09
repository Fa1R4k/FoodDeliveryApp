package com.example.fooddeliveryapp.data.database

import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    fun getAll(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: CartEntity)


    @Delete
    fun delete(product: CartEntity)

}