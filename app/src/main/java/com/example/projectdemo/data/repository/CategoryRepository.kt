package com.example.projectdemo.data.repository

import com.example.projectdemo.data.remote.api.CategoryApiService
import com.example.projectdemo.domain.mapper.CategoryMapper
import com.example.projectdemo.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}

class CategoryRepositoryImpl(
    private val categoryApiService: CategoryApiService
) : CategoryRepository {
    
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val response = categoryApiService.getCategories()
            val domainCategories = CategoryMapper.toDomainList(response.data.data)
            Result.success(domainCategories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
