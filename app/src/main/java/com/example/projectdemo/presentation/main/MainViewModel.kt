package com.example.projectdemo.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.projectdemo.base.BaseViewModel
import com.example.projectdemo.data.device.DeviceInfoProvider
import com.example.projectdemo.domain.usecase.SilentLoginUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val silentLoginUseCase: SilentLoginUseCase,
    private val deviceInfoProvider: DeviceInfoProvider
) : BaseViewModel<MainContract.State, MainContract.Intent, MainContract.Effect>(MainContract.State()) {
    
    companion object {
        private const val TAG = "MainViewModel"
    }
    
    override fun processIntent(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.SilentLogin -> {
                silentLogin()
            }
        }
    }
    
    private fun silentLogin() {
        viewModelScope.launch {
            setState { copy(isLoading = true, authError = null) }
            
            // Tự động lấy device ID từ DeviceInfoProvider
            val deviceId = deviceInfoProvider.getDeviceId()
            Log.d(TAG, "🔑 Device ID: $deviceId")
            
            silentLoginUseCase(deviceId)
                .onSuccess { auth ->
                    Log.i(TAG, "✅ Login thành công với Device ID: $deviceId")
                    Log.d(TAG, "📱 Access Token: ${auth.accessToken}")
                    Log.d(TAG, "🔄 Refresh Token: ${auth.refreshToken}")
                    Log.d(TAG, "⏰ Expires In: ${auth.expiresIn}s")
                    
                    setState { 
                        copy(
                            isLoading = false, 
                            isAuthenticated = true,
                            authError = null
                        ) 
                    }
                    setEffect { 
                        MainContract.Effect.LoginSuccess(
                            "Login thành công!"
                        ) 
                    }
                }
                .onFailure { error ->
                    Log.e(TAG, "❌ Login thất bại với Device ID: $deviceId")
                    Log.e(TAG, "❌ Error: ${error.message}", error)
                    
                    setState { 
                        copy(
                            isLoading = false, 
                            isAuthenticated = false,
                            authError = error.message
                        ) 
                    }
                    setEffect { 
                        MainContract.Effect.LoginError(
                            error.message ?: "Đã xảy ra lỗi khi đăng nhập"
                        ) 
                    }
                }
        }
    }
}
