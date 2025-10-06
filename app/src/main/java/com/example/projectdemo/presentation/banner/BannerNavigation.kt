package com.example.projectdemo.presentation.banner

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object BannerNavigation

fun NavGraphBuilder.bannerScreen(
    onNavigateBack: () -> Unit
) {
    composable<BannerNavigation> {
        BannerScreen(
            onBackPressed = onNavigateBack
        )
    }
}

fun NavController.navigateToBanner() {
    navigate(BannerNavigation)
}
