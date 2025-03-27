package com.example.p12_joiefull.di

import com.example.p12_joiefull.data.remote.ApiService
import com.example.p12_joiefull.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Le module est lié au cycle de vie de l'application
object NetworkModule {

    @Provides
    @Singleton
    fun provideCurrencyApiService(): ApiService {
        return RetrofitInstance.api  // Utilise RetrofitInstance pour fournir CurrencyApiService
    }
}