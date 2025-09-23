package com.example.projectdemo.presentation.splash

import com.example.projectdemo.core.viewmodel.MviContract

interface SplashContract  {

    data class State(
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object FetchRemote : Intent()
        object TrackSplashView: Intent()
    }

    sealed class Effect : MviContract.Effect {
        object NavigateToMain : Effect()
    }
}
