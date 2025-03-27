package com.example.p12_joiefull.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.p12_joiefull.model.Picture
import com.example.p12_joiefull.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(viewModel: MainActivityViewModel, modifier: Modifier = Modifier) {
    // Observe la liste des produits avec collectAsState()
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            Surface(modifier = modifier) {
                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    val groupedProducts = products.groupBy { it.category }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        groupedProducts.forEach { (category, productList) ->
                            item {
                                Text(
                                    text = category.replaceFirstChar { it.uppercase() }.lowercase().replaceFirstChar { it.uppercase() },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                LazyRow(modifier = Modifier.fillMaxSize()) {
                                    items(productList) { product ->
                                        ProductItem(
                                            product = product,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .clickable {
                                                    navController.navigate("detail_item/${product.id}")
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        composable("detail_item/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()

            productId?.let { id ->
                val product = products.find { it.id == id }
                product?.let {
                    ProductDetail(viewModel, navController, product)
                }
            }
        }
    }
}
class FakeViewModel : MainActivityViewModel(FakeRepository()) {
    override val products: StateFlow<List<Product>> = MutableStateFlow(
        listOf(
            Product(
                id = 1,
                name = "Robe élégante",
                category = "Vêtements",
                picture = Picture(
                    url = "https://via.placeholder.com/150",
                    description = "Image d'une robe élégante"
                ),
                price = 49.99,
                originalPrice = 79.99,
                likes = 120
            )
        )
    )
    override val isLoading: StateFlow<Boolean> = MutableStateFlow(false)
}
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(viewModel = FakeViewModel())
}