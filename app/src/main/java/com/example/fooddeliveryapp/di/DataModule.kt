package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.CartRepositoryImpl
import com.example.fooddeliveryapp.data.ProductRepositoryImpl
import com.example.fooddeliveryapp.data.UserRepositoryImpl
import com.example.fooddeliveryapp.domain.CartRepository
import com.example.fooddeliveryapp.domain.ProductRepository
import com.example.fooddeliveryapp.domain.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun getProductRepository(impl: ProductRepositoryImpl): ProductRepository


    @Binds
    @Singleton
    abstract fun getCartRepository(impl: CartRepositoryImpl): CartRepository


    @Binds
    @Singleton
    abstract fun getUserRepository(impl: UserRepositoryImpl): UserRepository

}