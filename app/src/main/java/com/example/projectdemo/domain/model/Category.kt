package com.example.projectdemo.domain.model

/**
 * Domain model cho Category
 * Được sử dụng trong presentation layer và business logic
 */
data class Category(
    val id: Int,
    val documentId: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
