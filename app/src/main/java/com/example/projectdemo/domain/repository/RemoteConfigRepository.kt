package com.example.projectdemo.domain.repository

interface RemoteConfigRepository {
    suspend fun fetchRemoteConfig(): Result<Boolean>
}