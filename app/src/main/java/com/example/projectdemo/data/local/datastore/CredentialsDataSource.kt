package com.example.projectdemo.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CredentialsDataSource(
    private val dataStore: DataStore<Preferences>
) {
    
    private object Key {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val TOKEN_EXPIRATION_TIME = longPreferencesKey("TOKEN_EXPIRATION_TIME")
    }

    fun getAccessToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[Key.ACCESS_TOKEN] ?: ""
        }
    }

    suspend fun getAccessTokenValue(): String {
        return getAccessToken().first()
    }

    suspend fun setAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[Key.ACCESS_TOKEN] = token
        }
    }

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences[Key.ACCESS_TOKEN] = ""
        }
    }

    fun getRefreshToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[Key.REFRESH_TOKEN] ?: ""
        }
    }
    
    suspend fun getRefreshTokenValue(): String {
        return getRefreshToken().first()
    }
    
    suspend fun setRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[Key.REFRESH_TOKEN] = token
        }
    }
    
    suspend fun getTokenExpirationTime(): Long {
        return dataStore.data.map { preferences ->
            preferences[Key.TOKEN_EXPIRATION_TIME] ?: 0L
        }.first()
    }
    
    suspend fun setTokenExpirationTime(expirationTime: Long) {
        dataStore.edit { preferences ->
            preferences[Key.TOKEN_EXPIRATION_TIME] = expirationTime
        }
    }

    suspend fun isAuthenticated(): Boolean {
        val token = getAccessTokenValue()
        return token.isNotEmpty() && !isTokenExpired()
    }
    
    /**
     * Check if the current access token is expired
     */
    suspend fun isTokenExpired(): Boolean {
        val expirationTime = getTokenExpirationTime()
        val currentTime = System.currentTimeMillis()
        return expirationTime > 0 && currentTime >= expirationTime
    }
    
    /**
     * Check if the token will expire within the specified buffer time (in milliseconds)
     * Default buffer is 5 minutes (300,000 milliseconds) to refresh before expiration
     */
    suspend fun isTokenExpiringSoon(bufferTimeMs: Long = 300_000L): Boolean {
        val expirationTime = getTokenExpirationTime()
        val currentTime = System.currentTimeMillis()
        return expirationTime > 0 && (currentTime + bufferTimeMs) >= expirationTime
    }
    
    /**
     * Store authentication data from AuthResponse
     * Calculates expiration timestamp from expiresIn seconds
     */
    suspend fun storeAuthData(accessToken: String, refreshToken: String, expiresInSeconds: Int) {
        val currentTimeMs = System.currentTimeMillis()
        val expirationTimeMs = currentTimeMs + (expiresInSeconds * 1000L)
        
        setAccessToken(accessToken)
        setRefreshToken(refreshToken)
        setTokenExpirationTime(expirationTimeMs)
    }
    
    /**
     * Clear all authentication data
     */
    suspend fun clearAllAuthData() {
        clearAccessToken()
        setRefreshToken("")
        setTokenExpirationTime(0L)
    }
}