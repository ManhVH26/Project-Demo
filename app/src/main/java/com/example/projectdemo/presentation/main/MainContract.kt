package com.example.projectdemo.presentation.main

import com.example.projectdemo.core.viewmodel.MviContract

interface MainContract {

    data class State(
        val appTitle: String = "",
        val isFeatureEnabled: Boolean = false,
        val maxRetryCount: Int = 0
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
    }

    sealed class Effect : MviContract.Effect
}
