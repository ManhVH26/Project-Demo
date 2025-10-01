package com.example.projectdemo.domain.model

/**
 * Domain model cho Auth
 * Được sử dụng trong presentation layer và business logic
 */
data class Auth(
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshExpiresIn: Int
)
