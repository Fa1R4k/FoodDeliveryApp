package com.example.fooddeliveryapp.di

import android.content.Context
import androidx.room.Room
import com.example.fooddeliveryapp.data.database.AppDataBase
import com.example.fooddeliveryapp.data.database.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database-name")
            .build()
    }

    @Provides
    fun provideProductDao(db: AppDataBase): CartDao = db.getCartDao()
}