package com.example.p12_joiefull.ui.screens.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProductDetailEvent>()
    val events = _events.asSharedFlow()

    fun loadProduct(product: Product) {
        _uiState.value = _uiState.value.copy(product = product, isFavorite = false)
    }

    fun toggleFavorite() {
        val newState = !_uiState.value.isFavorite
        _uiState.value = _uiState.value.copy(isFavorite = newState)
    }

    fun updateRating(newRating: Float) {
        _uiState.value = _uiState.value.copy(rating = newRating)
    }

    fun shareProduct(context: Context) {
        viewModelScope.launch {
            _uiState.value.product?.let { product ->
                _events.emit(ProductDetailEvent.ShareProduct(product))
            }
        }
    }

    data class ProductDetailUiState(
        val product: Product? = null,
        val isFavorite: Boolean = false,
        val rating: Float = 0f
    )

    sealed class ProductDetailEvent {
        data class ShareProduct(val product: Product) : ProductDetailEvent()
    }
}