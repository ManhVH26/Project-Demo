package com.example.projectdemo.data.remote.api

import com.example.projectdemo.data.model.AuthResponse

interface AuthApiService {
    suspend fun silentLogin(deviceId: String): AuthResponse
}
