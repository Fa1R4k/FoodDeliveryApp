package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.CartRepositoryImpl
import com.example.fooddeliveryapp.data.ProductRepositoryImpl
import com.example.fooddeliveryapp.data.UserRepositoryImpl
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.ProductRepository
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun getProductRepository(impl: ProductRepositoryImpl): ProductRepository


    @Binds
    abstract fun getCartRepository(impl: CartRepositoryImpl): CartRepository


    @Binds
    abstract fun getUserRepository(impl: UserRepositoryImpl): UserRepository

}