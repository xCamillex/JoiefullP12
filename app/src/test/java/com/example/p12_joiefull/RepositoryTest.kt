package com.example.p12_joiefull

import com.example.p12_joiefull.data.remote.ApiService
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.domain.model.Picture
import com.example.p12_joiefull.domain.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class RepositoryTest {

    //Test du cas de succès : Récupération des produits avec succès
    @Test
    fun `getProducts should return the list of products from apiService on success`() = runBlocking {
        // Arrange
        val mockApiService = mockk<ApiService>()
        val expectedProducts = listOf(
            Product(
                id = 1,
                picture = Picture("url1", "desc1"),
                name = "Product 1",
                category = "Category A",
                likes = 10,
                rating = 4.5f,
                price = 20.0,
                originalPrice = 15.0
            ),
            Product(
                id = 2,
                picture = Picture("url2", "desc2"),
                name = "Product 2",
                category = "Category B",
                likes = 5,
                rating = 3.8f,
                price = 30.0,
                originalPrice = 25.0
            )
        )
        coEvery { mockApiService.getProducts() } returns expectedProducts
        val repository = Repository(mockApiService)

        // Act
        val actualProducts = repository.getProducts()

        // Assert
        assertEquals(expectedProducts, actualProducts)
    }

    //Test du cas d'échec : Erreur lors de la récupération des produits depuis l'API
    @Test
    fun `getProducts should return an empty list when apiService throws an exception`() = runBlocking {
        // Arrange
        val mockApiService = mockk<ApiService>()
        coEvery { mockApiService.getProducts() } throws IOException("Network error")
        val repository = Repository(mockApiService)

        // Act
        val actualProducts = repository.getProducts()

        // Assert
        assertTrue(actualProducts.isEmpty())
    }
}