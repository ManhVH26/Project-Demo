package com.example.projectdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.projectdemo.presentation.banner.bannerScreen
import com.example.projectdemo.presentation.banner.navigateToBanner
import com.example.projectdemo.presentation.main.MainNavigation
import com.example.projectdemo.presentation.main.mainScreen
import com.example.projectdemo.presentation.main.navigateToMain

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigation
    ) {
        mainScreen(
            onNavigateToBanner = { navController.navigateToBanner() },
            onNavigateToInterstitial = { /* TODO: Implement */ },
            onNavigateToRewarded = { /* TODO: Implement */ },
            onNavigateToNative = { /* TODO: Implement */ },
            onNavigateToAppOpen = { /* TODO: Implement */ }
        )
        
        bannerScreen(
            onNavigateBack = { navController.navigateToMain() }
        )
        
        // TODO: Add other screens
        // interstitialScreen(...)
        // rewardedScreen(...)
        // nativeScreen(...)
        // appOpenScreen(...)
    }
}
