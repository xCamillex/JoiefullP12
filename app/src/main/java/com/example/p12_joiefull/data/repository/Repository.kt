package com.example.p12_joiefull.data.repository

import com.example.p12_joiefull.data.remote.ApiService
import com.example.p12_joiefull.domain.model.Product
import javax.inject.Inject


open class Repository @Inject constructor(
    private val apiService: ApiService
) {

    open suspend fun getProducts(): List<Product> {
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