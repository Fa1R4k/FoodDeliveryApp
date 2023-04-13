package com.example.fooddeliveryapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SourceModule {

    @Provides
    fun providePrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_KEY = "prefs_key"
    }
}