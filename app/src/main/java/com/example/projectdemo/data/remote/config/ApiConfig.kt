package com.example.projectdemo.data.remote.config

/**
 * API configuration constants including URLs, endpoints, timeouts and headers
 */
object ApiConfig {
    const val TIMEOUT_MILLIS = 30_000L
    // Base URLs
    const val MOVIE_BASE_URL = "https://api-drama-reel.apero.vn"
    const val AUTH_BASE_URL = "https://account-service.apero.vn"
    
    // API Endpoints
    const val AUTH_SILENT_LOGIN_ENDPOINT = "/saas-user-service/v1/users/silent-login"
    const val CATEGORY_ENDPOINT = "/api/strapi/be-drama-reel-film-categories"
    
    // Headers
    const val BEARER_PREFIX = "Bearer "
    const val HEADER_X_API_BUNDLE_ID = "x-api-bundleid"
    const val HEADER_X_API_BUNDLE_ID_VALUE = "com.dramafy.shortdrama.reelsmovie"
}
