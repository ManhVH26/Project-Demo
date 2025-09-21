package com.example.projectdemo.presentation.main

import com.example.projectdemo.core.configuration.AppRemoteConfiguration
import com.example.projectdemo.core.viewmodel.BaseViewModel

class MainViewModel(
    private val appRemoteConfig: AppRemoteConfiguration
) : BaseViewModel<MainContract.State, MainContract.Intent, MainContract.Effect>(
    initialState = MainContract.State()
) {

    override fun processIntent(intent: MainContract.Intent) {
        when (intent) {
            MainContract.Intent.LoadData -> loadRemoteValues()
        }
    }

    private fun loadRemoteValues() {
        setState {
            copy(
                appTitle = appRemoteConfig.getAppTitle(),
                isFeatureEnabled = appRemoteConfig.getIsFeatureEnabled(),
                maxRetryCount = appRemoteConfig.getMaxRetryCount()
            )
        }
    }
}
