package com.example.projectdemo.presentation.main

import com.example.projectdemo.base.MviContract

interface MainContract : MviContract<MainContract.State, MainContract.Intent, MainContract.Effect> {
    
    // State
    data class State(
        val isLoading: Boolean = false,
        val data: String = "",
        val userText: String = ""
    ) : MviContract.State

    // Intent
    sealed class Intent : MviContract.Intent {
        object LoadData : Intent()
        data class UpdateText(val text: String) : Intent()
        object ClearText : Intent()
    }

    // Effect
    sealed class Effect : MviContract.Effect {
        data class ShowMessage(val message: String) : Effect()
        object NavigateToNext : Effect()
    }
}
