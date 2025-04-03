package com.example.p12_joiefull.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.p12_joiefull.data.remote.ApiService
import com.example.p12_joiefull.data.repository.Repository
import com.example.p12_joiefull.domain.model.Picture
import com.example.p12_joiefull.domain.model.Product
import com.example.p12_joiefull.ui.screens.detail.ProductDetail
import com.example.p12_joiefull.ui.components.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(viewModel: MainScreenViewModel, navHostController: NavHostController) {
    // Observe la liste des produits avec collectAsState()
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            if (products.isEmpty()) {
                Text("Aucun produit trouvé")
            } else {
                BoxWithConstraints {
                    val screenWidth = maxWidth
                    //Vérifie si l'appareil est un smartphone ou une tablette et adapter l'affichage
                    if (screenWidth < 600.dp) {
                        SmartphoneMainScreen(products, navHostController)
                    } else {
                        TabletMainScreen(products, navHostController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SmartphoneMainScreen(
    productList: List<Product>,
    navHostController: NavHostController,
) {

    val groupedProducts = productList.groupBy { it.category }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        groupedProducts.forEach { (category, productList) ->
            item {
                Text(
                    text = category.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                        .semantics {
                            contentDescription = "Catégorie : $category"
                        }
                )
                LazyRow(modifier = Modifier.fillMaxSize()) {
                    items(productList) { product ->
                        ProductItem(
                            product = product,
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    navHostController.navigate("detail_item/${product.id}")
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabletMainScreen(
    productList: List<Product>,
    navHostController: NavHostController,
    viewModel: MainScreenViewModel
) {
    var itemSelected by remember {
        mutableStateOf<Product?>(null)
    }

    val groupedProducts = productList.groupBy { it.category }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
            contentPadding = PaddingValues(16.dp)
        )
        {
            groupedProducts.forEach { (category, products) ->
                item {
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                            .semantics {
                                contentDescription = "Catégorie : $category"
                            }
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(products) { product ->
                            ProductItem(
                                product = product,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        itemSelected = product
                                    }
                            )
                        }
                    }
                }
            }
        }

        // Affichage du détail du produit
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            itemSelected?.let { product ->
                ProductDetail(
                    viewModel,
                    navHostController,
                    product.id,
                    Modifier,
                    showBackButton = false
                )
            }
        }
    }
}

class FakeRepository : Repository(apiService = FakeApiService()) {
    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Robe élégante",
                category = "Vêtements",
                picture = Picture(
                    url = "https://via.placeholder.com/150",
                    description = "Image d'une robe élégante"
                ),
                likes = 120,
                rating = 4.6f,
                price = 49.99,
                originalPrice = 79.99
            )
        )
    }
}

// Simuler un ApiService factice
class FakeApiService : ApiService {
    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Robe élégante",
                category = "Sample Category",
                picture = Picture(
                    url = "https://via.placeholder.com/150",
                    description = "Image d'une robe élégante"
                ),
                likes = 120,
                rating = 4.6f,
                price = 49.99,
                originalPrice = 79.99
            )
        )
    }
}

class FakeViewModel : MainScreenViewModel(FakeRepository()) {
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
                rating = 4.6f,
                likes = 120
            )
        )
    )
    override val isLoading: StateFlow<Boolean> = MutableStateFlow(false)
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    MainScreen(viewModel = FakeViewModel(), navHostController = navController)
}
