package com.example.projectdemo.base



interface MviContract<S : MviContract.State, I : MviContract.Intent, E : MviContract.Effect> {

    interface State

    interface Intent

    interface Effect

    val state: S

    fun processIntent(intent: I)
}