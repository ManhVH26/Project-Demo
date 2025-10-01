package com.example.projectdemo.data.device

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

interface DeviceInfoProvider {
    fun getDeviceId(): String
}

class AndroidDeviceInfoProvider(
    private val context: Context
) : DeviceInfoProvider {
    
    /**
     * Get unique device ID (Android ID)
     * This is a 64-bit number (as a hex string) that is randomly generated when the user
     * first sets up the device and should remain constant for the lifetime of the user's device.
     */
    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return try {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "unknown_device"
        } catch (e: Exception) {
            "unknown_device"
        }
    }
}

