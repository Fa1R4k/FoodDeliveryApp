package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.RepositoryImpl
import com.example.fooddeliveryapp.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun getProduct(impl: RepositoryImpl): Repository
}