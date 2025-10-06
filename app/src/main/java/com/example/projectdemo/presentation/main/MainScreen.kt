package com.example.projectdemo.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

data class AdType(
    val title: String,
    val intent: MainContract.Intent
)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    onNavigateToBannerAds: () -> Unit = {},
    onNavigateToInterstitialAds: () -> Unit = {},
    onNavigateToRewardedAds: () -> Unit = {},
    onNavigateToNativeAds: () -> Unit = {},
    onNavigateToAppOpenAds: () -> Unit = {}
) {
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    // Handle navigation effects
    LaunchedEffect(effect) {
        effect?.let { currentEffect ->
            when (currentEffect) {
                is MainContract.Effect.NavigateToBannerAds -> onNavigateToBannerAds()
                is MainContract.Effect.NavigateToInterstitialAds -> onNavigateToInterstitialAds()
                is MainContract.Effect.NavigateToRewardedAds -> onNavigateToRewardedAds()
                is MainContract.Effect.NavigateToNativeAds -> onNavigateToNativeAds()
                is MainContract.Effect.NavigateToAppOpenAds -> onNavigateToAppOpenAds()
            }
        }
    }

    val adTypes = listOf(
        AdType(
            title = "Banner Ads",
            intent = MainContract.Intent.NavigateToBannerAds
        ),
        AdType(
            title = "Interstitial Ads",
            intent = MainContract.Intent.NavigateToInterstitialAds
        ),
        AdType(
            title = "Rewarded Ads",
            intent = MainContract.Intent.NavigateToRewardedAds
        ),
        AdType(
            title = "Native Ads",
            intent = MainContract.Intent.NavigateToNativeAds
        ),
        AdType(
            title = "App Open Ads",
            intent = MainContract.Intent.NavigateToAppOpenAds
        )
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AdMob Demo",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Ad Types List
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            adTypes.forEach { adType ->
                AdTypeButton(
                    adType = adType,
                    onClick = { viewModel.processIntent(adType.intent) }
                )
            }
        }
    }
}

@Composable
private fun AdTypeButton(
    adType: AdType,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = adType.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
