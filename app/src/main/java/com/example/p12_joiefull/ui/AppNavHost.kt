package com.example.p12_joiefull.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.p12_joiefull.model.Product

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