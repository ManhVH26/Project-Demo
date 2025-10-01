package com.example.projectdemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("correlationId")
    val correlationId: String,
    @SerialName("path")
    val path: String,
    @SerialName("data")
    val data: AuthData
)

@Serializable
data class AuthData(
    @SerialName("tokenType")
    val tokenType: String,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("expiresIn")
    val expiresIn: Int,
    @SerialName("refreshExpiresIn")
    val refreshExpiresIn: Int
)
