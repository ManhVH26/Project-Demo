package com.example.projectdemo.presentation.main

import com.example.projectdemo.core.viewmodel.MviContract

interface MainContract {

    data class State(
        val appTitle: String = "",
        val isFeatureEnabled: Boolean = false,
        val maxRetryCount: Int = 0,
        val eventsTracked: Int = 0
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        object TrackMainView : Intent()
        object TrackButtonClick : Intent()
        object TrackFeatureUsed : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowTrackingInfo(val message: String) : Effect()
    }
}
