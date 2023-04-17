package com.example.fooddeliveryapp.di.modules

import com.example.fooddeliveryapp.data.repositoriesImpl.CartRepositoryImpl
import com.example.fooddeliveryapp.data.repositoriesImpl.ProductRepositoryImpl
import com.example.fooddeliveryapp.data.repositoriesImpl.UserRepositoryImpl
import com.example.fooddeliveryapp.domain.repositories.CartRepository
import com.example.fooddeliveryapp.domain.repositories.ProductRepository
import com.example.fooddeliveryapp.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun getProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    fun getCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    fun getUserRepository(impl: UserRepositoryImpl): UserRepository
}