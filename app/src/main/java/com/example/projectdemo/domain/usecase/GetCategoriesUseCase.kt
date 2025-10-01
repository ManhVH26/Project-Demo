package com.example.projectdemo.domain.usecase

import com.example.projectdemo.domain.model.Category
import com.example.projectdemo.data.repository.CategoryRepository

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<Category>> {
        return categoryRepository.getCategories()
    }
}
