package com.example.projectdemo.presentation.main

import com.example.projectdemo.base.BaseViewModel

class MainViewModel : BaseViewModel<MainContract.State, MainContract.Intent, MainContract.Effect>(MainContract.State()) {
    
    override fun processIntent(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.NavigateToBannerAds -> {
                setEffect { MainContract.Effect.NavigateToBannerAds }
            }
            is MainContract.Intent.NavigateToInterstitialAds -> {
                setEffect { MainContract.Effect.NavigateToInterstitialAds }
            }
            is MainContract.Intent.NavigateToRewardedAds -> {
                setEffect { MainContract.Effect.NavigateToRewardedAds }
            }
            is MainContract.Intent.NavigateToNativeAds -> {
                setEffect({ MainContract.Effect.NavigateToNativeAds })
            }
            is MainContract.Intent.NavigateToAppOpenAds -> {
                setEffect { MainContract.Effect.NavigateToAppOpenAds }
            }
        }
    }
}
