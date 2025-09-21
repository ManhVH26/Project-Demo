package com.example.projectdemo.data.datasource

interface RemoteConfigDataSource {
    suspend fun fetchRemoteConfigs(): Map<String, Any?>
}