package com.example.projectdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.projectdemo.presentation.main.mainScreen
import com.example.projectdemo.presentation.main.navigateToMain
import com.example.projectdemo.presentation.splash.SplashDestination
import com.example.projectdemo.presentation.splash.splashScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashDestination
    ) {
        splashScreen(
            onNavigateToMain = {
                navController.navigateToMain()
            }
        )
        mainScreen()
    }
}
