package com.example.projectdemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API 2: Lấy danh sách danh mục phim
 * Endpoint: /api/strapi/be-drama-x-film-categories
 */
@Serializable
data class CategoryResponse(
    @SerialName("data")
    val data: CategoryData,
    @SerialName("meta")
    val meta: Meta
)

@Serializable
data class CategoryData(
    @SerialName("data")
    val data: List<CategoryItem>,
    @SerialName("meta")
    val meta: PaginationMeta
)

@Serializable
data class CategoryItem(
    @SerialName("id")
    val id: Int,
    @SerialName("documentId")
    val documentId: String,
    @SerialName("name")
    val name: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("publishedAt")
    val publishedAt: String
)

@Serializable
data class PaginationMeta(
    @SerialName("pagination")
    val pagination: Pagination
)

@Serializable
data class Pagination(
    @SerialName("page")
    val page: Int,
    @SerialName("pageSize")
    val pageSize: Int,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("total")
    val total: Int
)

@Serializable
data class Meta(
    @SerialName("code")
    val code: Int,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("path")
    val path: String,
    @SerialName("traceId")
    val traceId: String
)
