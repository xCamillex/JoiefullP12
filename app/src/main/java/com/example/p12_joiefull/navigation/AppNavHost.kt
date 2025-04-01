package com.example.p12_joiefull.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.p12_joiefull.domain.model.Product
import com.example.p12_joiefull.ui.screens.main.MainActivityViewModel
import com.example.p12_joiefull.ui.screens.main.MainScreen
import com.example.p12_joiefull.ui.screens.detail.ProductDetail

@Composable
fun AppNavHost(navController: NavHostController, products: List<Product>, viewModel: MainActivityViewModel) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(viewModel, navController)
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