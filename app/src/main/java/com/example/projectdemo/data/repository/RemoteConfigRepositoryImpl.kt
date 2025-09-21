package com.example.projectdemo.data.repository

import com.example.projectdemo.core.configuration.AppRemoteConfiguration
import com.example.projectdemo.data.datasource.RemoteConfigDataSource
import com.example.projectdemo.domain.repository.RemoteConfigRepository

class RemoteConfigRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource,
    private val appConfig: AppRemoteConfiguration
) : RemoteConfigRepository {

    override suspend fun fetchRemoteConfig(): Result<Boolean> {
        return try {
            val remoteMap = remoteConfigDataSource.fetchRemoteConfigs()
            appConfig.syncAllConfigs(remoteMap)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

