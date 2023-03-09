package com.example.fooddeliveryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCartDao(): CartDao
}
