package com.example.projectdemo.data.remote.interceptor

import com.example.projectdemo.data.local.datastore.CredentialsDataSource
import com.example.projectdemo.data.remote.config.ApiConfig
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking

/**
 * Interceptor that adds Authorization header to HTTP requests
 * Should only be used for data client (not auth client)
 * 
 * Gets the access token from CredentialsDataSource and adds it to the Authorization header
 */
fun createAuthInterceptor(credentialsDataSource: CredentialsDataSource) = createClientPlugin("AuthInterceptor") {
    onRequest { request, _ ->
        // Get token from DataStore
        val token = runBlocking { 
            credentialsDataSource.getAccessTokenValue() 
        }
        
        if (token.isNotEmpty()) {
            request.header(
                HttpHeaders.Authorization, 
                ApiConfig.BEARER_PREFIX + token
            )
        }
    }
}
