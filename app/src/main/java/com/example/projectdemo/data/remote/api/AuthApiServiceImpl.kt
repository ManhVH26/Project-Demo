package com.example.projectdemo.data.remote.api

import com.example.projectdemo.data.model.AuthResponse
import com.example.projectdemo.data.remote.config.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.Serializable

@Serializable
private data class AuthRequest(
    val deviceId: String
)

class AuthApiServiceImpl(
    private val httpClient: HttpClient
) : AuthApiService {

    override suspend fun silentLogin(deviceId: String): AuthResponse {
        val response = httpClient.post(ApiConfig.AUTH_SILENT_LOGIN_ENDPOINT) {
            setBody(AuthRequest(deviceId = deviceId))
        }.body<AuthResponse>()
        return response
    }
}
