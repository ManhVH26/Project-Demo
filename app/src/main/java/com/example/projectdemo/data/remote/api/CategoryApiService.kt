package com.example.projectdemo.data.remote.api

import com.example.projectdemo.data.model.CategoryResponse

interface CategoryApiService {
    suspend fun getCategories(): CategoryResponse
}
