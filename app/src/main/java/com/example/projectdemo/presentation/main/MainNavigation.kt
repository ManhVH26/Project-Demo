package com.example.projectdemo.presentation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainDestination

fun NavController.navigateToMain() {
    navigate(MainDestination) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.mainScreen() {
    composable<MainDestination> {
        MainScreen()
    }
}
