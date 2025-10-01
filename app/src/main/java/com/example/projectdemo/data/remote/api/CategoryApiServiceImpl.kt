package com.example.projectdemo.data.remote.api

import com.example.projectdemo.data.model.CategoryResponse
import com.example.projectdemo.data.remote.config.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CategoryApiServiceImpl(
    private val httpClient: HttpClient
) : CategoryApiService {
    
    override suspend fun getCategories(): CategoryResponse {
        return httpClient.get(ApiConfig.CATEGORY_ENDPOINT).body()
    }
}
