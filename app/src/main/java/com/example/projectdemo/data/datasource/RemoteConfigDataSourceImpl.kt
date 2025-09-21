package com.example.projectdemo.data.datasource

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await


class RemoteConfigDataSourceImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigDataSource {

    override suspend fun fetchRemoteConfigs(): Map<String, Any?> {
        try {
            remoteConfig.fetchAndActivate().await()
            val allConfigs = remoteConfig.all.mapValues { it.value.asString() }
            return allConfigs
        } catch (e: Exception) {
            Log.e("RemoteConfig", "Error fetching remote config: ${e.message}", e)
            throw e
        }
    }
}
