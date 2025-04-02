package com.example.p12_joiefull

import app.cash.turbine.test
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.domain.model.Picture
import com.example.p12_joiefull.domain.model.Product
import com.example.p12_joiefull.ui.screens.main.MainScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest {

    private lateinit var viewModel: MainScreenViewModel
    private val repository: Repository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainScreenViewModel(repository)
    }

    //products → Vérifie que la liste des produits est bien initialisée à vide.
    @Test
    fun `products should be empty initially`() = runTest {
        viewModel.products.test {
            assertTrue(awaitItem().isEmpty()) // Vérifie que la première valeur est vide
        }
    }

    //getProductList → Vérifie que la liste des produits est bien mise à jour.
    @Test
    fun `getProductList should update products`() = runTest {
        // Simule une liste de produits
        val productList = listOf(
            Product(1, Picture("url1", "desc1"), "Product1", "Category1", 10, 4.5f, 19.99, 24.99),
            Product(2, Picture("url2", "desc2"), "Product2", "Category2", 5, 3.0f, 29.99, 39.99)
        )

        coEvery { repository.getProducts() } returns productList

        // Exécute la récupération des produits
        viewModel = MainScreenViewModel(repository) // L'init déclenche getProductList()
        advanceUntilIdle() // Attendre la fin des coroutines

        viewModel.products.test {
            assertEquals(productList, awaitItem()) // Vérifie que les produits sont bien mis à jour
        }
    }

    //isLoading → Vérifie que l’état de chargement est bien mis à jour.
    @Test
    fun `isLoading should be true during product fetch and false after`() = runTest {
        val viewModel = MainScreenViewModel(repository)

        viewModel.isLoading.test {
            // L'état initial doit être faux
            assertEquals(false, awaitItem())

            // Déclencher la récupération du produit
            viewModel.getProductList()

            // La première valeur émise doit être vraie (état de chargement)
            assertEquals(true, awaitItem())

            // Après la récupération du produit, l'état isLoading doit devenir faux
            assertEquals(false, awaitItem())

            // S'assure qu'aucun autre élément n'est émis
            cancelAndConsumeRemainingEvents()
        }
    }

    //getProducById → Vérifie que le produit retourné est le bon.
    @Test
    fun `getProductById should return correct product`() = runTest {
        val productList = listOf(
            Product(1, Picture("url1", "desc1"), "Product1", "Category1", 10, 4.5f, 19.99, 24.99)
        )

        coEvery { repository.getProducts() } returns productList

        viewModel = MainScreenViewModel(repository)
        advanceUntilIdle() // Attendre la fin des coroutines

        assertEquals("Product1", viewModel.getProductById(1)?.name)
        assertNull(viewModel.getProductById(2)) // Produit inexistant
    }
}