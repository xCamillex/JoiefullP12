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
import com.example.p12_joiefull.navigation.AppNavHost
import com.example.p12_joiefull.ui.screens.main.MainScreenViewModel

import com.example.p12_joiefull.ui.theme.P12_JoiefullTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            P12_JoiefullTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}