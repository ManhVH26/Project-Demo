package com.example.projectdemo.data.remote.client

import com.example.projectdemo.data.remote.config.ApiConfig
import com.example.projectdemo.data.remote.config.ApiConfig.TIMEOUT_MILLIS
import com.example.projectdemo.data.remote.interceptor.BundleIdInterceptor
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * HttpClient configuration for Auth API
 */
fun HttpClientConfig<*>.configureAuthClient() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("Auth HTTP Client: $message")
            }
        }
        level = LogLevel.INFO
    }

    install(HttpTimeout) {
        requestTimeoutMillis = TIMEOUT_MILLIS
        connectTimeoutMillis = TIMEOUT_MILLIS
        socketTimeoutMillis = TIMEOUT_MILLIS
    }

    // Install bundle ID interceptor only (no auth header for auth requests)
    install(BundleIdInterceptor)

    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        header(HttpHeaders.Accept, ContentType.Application.Json.toString())
        url(ApiConfig.AUTH_BASE_URL)
    }
}