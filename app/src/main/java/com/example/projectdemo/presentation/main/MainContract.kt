package com.example.projectdemo.presentation.main

import com.example.projectdemo.base.MviContract

interface MainContract : MviContract<MainContract.State, MainContract.Intent, MainContract.Effect> {
    
    // State
    data class State(
        val isLoading: Boolean = false
    ) : MviContract.State

    // Intent
    sealed class Intent : MviContract.Intent {
        object NavigateToBannerAds : Intent()
        object NavigateToInterstitialAds : Intent()
        object NavigateToRewardedAds : Intent()
        object NavigateToNativeAds : Intent()
        object NavigateToAppOpenAds : Intent()
    }

    // Effect
    sealed class Effect : MviContract.Effect {
        object NavigateToBannerAds : Effect()
        object NavigateToInterstitialAds : Effect()
        object NavigateToRewardedAds : Effect()
        object NavigateToNativeAds : Effect()
        object NavigateToAppOpenAds : Effect()
    }
}
