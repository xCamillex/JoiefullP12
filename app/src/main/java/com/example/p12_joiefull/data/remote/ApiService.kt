package com.example.p12_joiefull.data.remote

import com.example.p12_joiefull.domain.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("main/api/clothes.json")
    suspend fun getProducts(): List<Product>
}

object RetrofitInstance {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}