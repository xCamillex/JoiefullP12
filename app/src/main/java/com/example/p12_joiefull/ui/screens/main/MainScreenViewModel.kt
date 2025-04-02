package com.example.p12_joiefull.ui.screens.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainScreenViewModel @Inject constructor(
    private val productRepository: Repository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    open val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getProductList()
    }

    // Fonction pour récupérer la liste des produits
   private fun getProductList() {
        viewModelScope.launch {
            _isLoading.value = true
            val productsList = productRepository.getProducts()
            _products.value = productsList
            _isLoading.value = false
        }
    }
    fun shareProduct(context: Context, product: Product) {
        // Créez une intention de partage
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Regarde cet article : ${product.name} \n ${product.picture.url}")
        }

        // Utilisez le contexte pour démarrer l'intention et afficher le sélecteur de partage
        context.startActivity(Intent.createChooser(shareIntent, "Partage cet article via"))

    }
    fun getProductById(productId: Int): Product? {
        return _products.value.find { it.id == productId }
    }
}