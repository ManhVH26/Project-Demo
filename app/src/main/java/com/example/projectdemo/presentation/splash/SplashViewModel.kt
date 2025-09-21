package com.example.projectdemo.presentation.splash

import androidx.lifecycle.viewModelScope
import com.example.projectdemo.core.viewmodel.BaseViewModel
import com.example.projectdemo.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val remoteConfigRepository: RemoteConfigRepository
) : BaseViewModel<
        SplashContract.State,
        SplashContract.Intent,
        SplashContract.Effect
        >(SplashContract.State()) {

    override fun processIntent(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.FetchRemote -> fetchRemote()
        }
    }


    private fun fetchRemote() {
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val result =
                    kotlinx.coroutines.withTimeoutOrNull(5000L) {
                        remoteConfigRepository.fetchRemoteConfig()
                    }

                setState { copy(isLoading = false) }

                if (result?.isSuccess == true) {
                    setEffect { SplashContract.Effect.NavigateToMain }
                } else if (result?.isFailure == true) {
                    setEffect { SplashContract.Effect.NavigateToMain }
                } else {
                    setEffect { SplashContract.Effect.NavigateToMain }
                }
            } catch (_: Exception) {
                setState { copy(isLoading = false) }
                setEffect { SplashContract.Effect.NavigateToMain }
            }
        }
    }

}
