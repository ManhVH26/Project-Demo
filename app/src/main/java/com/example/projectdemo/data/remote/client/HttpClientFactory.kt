package com.example.projectdemo.data.remote.client

import com.example.projectdemo.data.local.datastore.CredentialsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

/**
 * Factory for creating HttpClient with common configuration
 */
class HttpClientFactory(
    private val credentialsDataSource: CredentialsDataSource
) {

    /**
     * Create HttpClient for Auth API
     */
    fun createAuthClient(): HttpClient {
        return HttpClient(Android) {
            configureAuthClient()
        }
    }
    
    /**
     * Create HttpClient for main API (Movie API)
     */
    fun createMovieClient(): HttpClient {
        return HttpClient(Android) {
            configureMovieClient(credentialsDataSource)
        }
    }
}
