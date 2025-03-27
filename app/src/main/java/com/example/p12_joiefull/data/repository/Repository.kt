package com.example.p12_joiefull.data.repository

import com.example.p12_joiefull.data.remote.ApiService
import com.example.p12_joiefull.model.Product
import javax.inject.Inject


class Repository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getProducts(): List<Product> {
        return try {
            val products = apiService.getProducts()
            println("Products retrieved: ${products.size}")
            products
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}