package com.example.projectdemo.presentation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SplashDestination

fun NavController.navigateToSplash() {
    navigate(SplashDestination) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.splashScreen(
    onNavigateToMain: () -> Unit
) {
    composable<SplashDestination> {
        SplashScreen(
            onNavigateToMain = onNavigateToMain
        )
    }
}
