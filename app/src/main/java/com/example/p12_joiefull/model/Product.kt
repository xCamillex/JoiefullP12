package com.example.p12_joiefull.model

import com.squareup.moshi.Json

data class Product(
    val id: Int,
    val picture: Picture,
    val name: String,
    val category: String,
    val likes: Int,
    val rating: Float = 3.5f,
    val price: Double,
    @Json(name = "original_price")
    val originalPrice: Double
)

data class Picture(
    val url: String,
    val description: String
)