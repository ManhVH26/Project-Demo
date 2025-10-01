package com.example.projectdemo.data.repository

import com.example.projectdemo.data.local.datastore.CredentialsDataSource
import com.example.projectdemo.data.remote.api.AuthApiService
import com.example.projectdemo.domain.mapper.AuthMapper
import com.example.projectdemo.domain.model.Auth

interface AuthRepository {
    suspend fun silentLogin(deviceId: String): Result<Auth>
}

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val credentialsDataSource: CredentialsDataSource
) : AuthRepository {
    
    override suspend fun silentLogin(deviceId: String): Result<Auth> {
        return try {
            val response = authApiService.silentLogin(deviceId)
            val domainAuth = AuthMapper.toDomain(response.data)
            
            // Lưu token vào DataStore sau khi login thành công
            credentialsDataSource.storeAuthData(
                accessToken = domainAuth.accessToken,
                refreshToken = domainAuth.refreshToken,
                expiresInSeconds = domainAuth.expiresIn
            )
            
            Result.success(domainAuth)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
