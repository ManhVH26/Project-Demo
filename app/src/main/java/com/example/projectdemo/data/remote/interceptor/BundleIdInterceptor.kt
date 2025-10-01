package com.example.projectdemo.data.remote.interceptor

import com.example.projectdemo.data.remote.config.ApiConfig
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.header

/**
 * Interceptor that adds the bundle ID header to all HTTP requests
 * Should be used for both auth and data clients
 */
val BundleIdInterceptor = createClientPlugin("BundleIdInterceptor") {
    onRequest { request, _ ->
        // Add bundle ID header to all requests
        request.header(
            ApiConfig.HEADER_X_API_BUNDLE_ID,
            ApiConfig.HEADER_X_API_BUNDLE_ID_VALUE
        )
    }
}
