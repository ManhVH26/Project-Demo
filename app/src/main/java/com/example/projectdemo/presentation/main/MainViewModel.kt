package com.example.projectdemo.presentation.main

import com.example.projectdemo.core.configuration.AppRemoteConfiguration
import com.example.projectdemo.core.viewmodel.BaseViewModel
import com.example.projectdemo.domain.tracking.UserJourneyTracker

class MainViewModel(
    private val appRemoteConfig: AppRemoteConfiguration,
    private val userJourneyTracker: UserJourneyTracker
) : BaseViewModel<MainContract.State, MainContract.Intent, MainContract.Effect>(
    initialState = MainContract.State()
) {

    override fun processIntent(intent: MainContract.Intent) {
        when (intent) {
            MainContract.Intent.LoadData -> loadRemoteValues()
            MainContract.Intent.TrackMainView -> trackMainView()
            MainContract.Intent.TrackButtonClick -> trackButtonClick()
            MainContract.Intent.TrackFeatureUsed -> trackFeatureUsed()
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

    private fun trackMainView() {
        userJourneyTracker.trackMainView()
        updateTrackingCount()
        setEffect { MainContract.Effect.ShowTrackingInfo("Main view tracked to Firebase") }
    }

    private fun trackButtonClick() {
        userJourneyTracker.trackButtonClick("main_button")
        updateTrackingCount()
        setEffect { MainContract.Effect.ShowTrackingInfo("Button click tracked to Firebase") }
    }

    private fun trackFeatureUsed() {
        userJourneyTracker.trackFeatureUsed("event_tracking")
        updateTrackingCount()
        setEffect { MainContract.Effect.ShowTrackingInfo("Feature used tracked to Firebase") }
    }

    private fun updateTrackingCount() {
        setState { 
            copy(eventsTracked = eventsTracked + 1) 
        }
    }
}
