package com.example.projectdemo.core.configuration

import android.content.SharedPreferences
import com.example.projectdemo.data.keys.RemoteKey

class AppRemoteConfiguration(
    sharedPrefs: SharedPreferences
) : BaseRemoteConfiguration(sharedPrefs) {

    override fun getAllKeys(): List<RemoteKey> =
        RemoteKey.getAllKeys()

    fun getAppTitle(): String = RemoteKey.AppTitle.get()
    fun getIsFeatureEnabled(): Boolean  = RemoteKey.IsFeatureEnabled.get()
    fun getMaxRetryCount(): Int = RemoteKey.MaxRetryCount.get()
}
