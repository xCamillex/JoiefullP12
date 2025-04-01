package com.example.p12_joiefull.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.p12_joiefull.ui.screens.main.MainScreenViewModel
import com.example.p12_joiefull.ui.screens.main.MainScreen
import com.example.p12_joiefull.ui.screens.detail.ProductDetail

@Composable
fun AppNavHost(navController: NavHostController, viewModel: MainScreenViewModel) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(viewModel, navController)
        }
        composable("detail_item/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                   ProductDetail(viewModel, navController, productId)
                } else {
                    // Gérez le cas où productId est null
                    Text("Produit non trouvé")
            }
        }
    }
}