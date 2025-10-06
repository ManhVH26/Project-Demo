package com.example.projectdemo.presentation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainNavigation

fun NavGraphBuilder.mainScreen(
    onNavigateToBanner: () -> Unit,
    onNavigateToInterstitial: () -> Unit,
    onNavigateToRewarded: () -> Unit,
    onNavigateToNative: () -> Unit,
    onNavigateToAppOpen: () -> Unit
) {
    composable<MainNavigation> {
        MainScreen(
            onNavigateToBannerAds = onNavigateToBanner,
            onNavigateToInterstitialAds = onNavigateToInterstitial,
            onNavigateToRewardedAds = onNavigateToRewarded,
            onNavigateToNativeAds = onNavigateToNative,
            onNavigateToAppOpenAds = onNavigateToAppOpen
        )
    }
}

fun NavController.navigateToMain() {
    navigate(MainNavigation)
}
