package com.example.fooddeliveryapp.di.modules

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
        return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: AppDataBase): CartDao = db.getCartDao()

    companion object{
        private const val DATABASE_NAME =  "database-name"
    }
}