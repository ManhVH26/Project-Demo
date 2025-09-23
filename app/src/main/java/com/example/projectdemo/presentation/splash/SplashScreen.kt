package com.example.projectdemo.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),

    onNavigateToMain: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.processIntent(SplashContract.Intent.TrackSplashView)
        viewModel.processIntent(SplashContract.Intent.FetchRemote)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            if (effect is SplashContract.Effect.NavigateToMain) {
                onNavigateToMain()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

