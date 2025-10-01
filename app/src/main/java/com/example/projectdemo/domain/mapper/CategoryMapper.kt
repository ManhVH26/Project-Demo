package com.example.projectdemo.domain.mapper

import com.example.projectdemo.data.model.CategoryItem
import com.example.projectdemo.domain.model.Category

/**
 * Mapper để convert từ API response sang domain model
 */
object CategoryMapper {
    
    /**
     * Convert CategoryItem (API response) sang Category (domain model)
     */
    fun toDomain(categoryItem: CategoryItem): Category {
        return Category(
            id = categoryItem.id,
            documentId = categoryItem.documentId,
            name = categoryItem.name,
            createdAt = categoryItem.createdAt,
            updatedAt = categoryItem.updatedAt,
            publishedAt = categoryItem.publishedAt
        )
    }
    
    /**
     * Convert list CategoryItem sang list Category
     */
    fun toDomainList(categoryItems: List<CategoryItem>): List<Category> {
        return categoryItems.map { toDomain(it) }
    }
}
