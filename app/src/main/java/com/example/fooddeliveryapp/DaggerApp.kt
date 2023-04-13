package com.example.fooddeliveryapp

import android.app.Application
import com.example.fooddeliveryapp.di.ApplicationComponent
import com.example.fooddeliveryapp.di.DaggerApplicationComponent

class DaggerApp : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}