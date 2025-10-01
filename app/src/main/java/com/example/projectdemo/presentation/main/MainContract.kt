package com.example.projectdemo.presentation.main

import com.example.projectdemo.base.MviContract

interface MainContract : MviContract<MainContract.State, MainContract.Intent, MainContract.Effect> {
    
    // State
    data class State(
        val isLoading: Boolean = false,
        val isAuthenticated: Boolean = false,
        val authError: String? = null
    ) : MviContract.State

    // Intent
    sealed class Intent : MviContract.Intent {
        object SilentLogin : Intent()
    }

    // Effect
    sealed class Effect : MviContract.Effect {
        data class LoginSuccess(val message: String) : Effect()
        data class LoginError(val error: String) : Effect()
    }
}
