package com.example.fooddeliveryapp.di

import android.content.Context
import androidx.room.Room
import com.example.fooddeliveryapp.data.database.AppDataBase
import com.example.fooddeliveryapp.data.database.CartDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database-name")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: AppDataBase): CartDao = db.getCartDao()
}