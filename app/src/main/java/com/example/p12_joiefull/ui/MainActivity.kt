package com.example.p12_joiefull.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController

import com.example.p12_joiefull.ui.theme.P12_JoiefullTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            P12_JoiefullTheme {
                val products by viewModel.products.collectAsState(initial = emptyList())
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    products = products,
                    viewModel = viewModel
                )

            }
        }
    }
}
