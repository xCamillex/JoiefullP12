package com.example.p12_joiefull

import com.example.p12_joiefull.domain.model.Picture
import com.example.p12_joiefull.domain.model.Product
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.ui.screens.detail.ProductDetailViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductDetailViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel
    private val repository: Repository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private val testProduct = Product(
        id = 1,
        picture = Picture("url", "desc"),
        name = "T-shirt",
        category = "Clothing",
        likes = 10,
        rating = 4.5f,
        price = 19.99,
        originalPrice = 29.99
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Nettoie Dispatchers.Main après le test
    }

    //loadProduct → Vérifie que l’état est bien mis à jour avec le produit.
    @Test
    fun `loadProduct should update uiState with product`() {
        viewModel.loadProduct(testProduct)

        val state = viewModel.uiState.value
        assertNotNull(state.product)
        assertEquals(testProduct, state.product)
        assertFalse(state.isFavorite) // Par défaut, `isFavorite` doit être `false`
    }

    //toggleFavorite → Vérifie que le statut favori change correctement (true ↔ false).
    @Test
    fun `toggleFavorite should switch isFavorite value`() {
        viewModel.loadProduct(testProduct)

        viewModel.toggleFavorite()
        assertTrue(viewModel.uiState.value.isFavorite)

        viewModel.toggleFavorite()
        assertFalse(viewModel.uiState.value.isFavorite)
    }

    //updateRating → Vérifie que la mise à jour de la note fonctionne.
    @Test
    fun `updateRating should modify the rating value`() {
        val newRating = 4.0f
        viewModel.updateRating(newRating)

        assertEquals(newRating, viewModel.uiState.value.rating, 0.0f)
    }

    //shareProduct →  Vérifie que l’événement de partage est bien émis.
    @Test
    fun `shareProduct should emit ShareProduct event`() = runTest {
        viewModel.loadProduct(testProduct)

        viewModel.shareProduct(mockk())

        val event = viewModel.events.first()
        assertTrue(event is ProductDetailViewModel.ProductDetailEvent.ShareProduct)
        assertEquals(testProduct, (event as ProductDetailViewModel.ProductDetailEvent.ShareProduct).product)
    }
}
