package com.example.projectdemo.presentation.main

import com.example.projectdemo.base.BaseViewModel

class MainViewModel : BaseViewModel<MainContract.State, MainContract.Intent, MainContract.Effect>(MainContract.State()) {
    
    override fun processIntent(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.LoadData -> {
                loadData()
            }
            is MainContract.Intent.UpdateText -> {
                updateText(intent.text)
            }
            is MainContract.Intent.ClearText -> {
                clearText()
            }
        }
    }
    
    private fun loadData() {
        setState {
            copy(isLoading = true)
        }
        
        // Simulate loading data
        setState {
            copy(
                isLoading = false,
                data = "Data loaded successfully!"
            )
        }
    }
    
    private fun updateText(text: String) {
        setState {
            copy(userText = text)
        }
    }
    
    private fun clearText() {
        setState {
            copy(userText = "")
        }
    }
}
