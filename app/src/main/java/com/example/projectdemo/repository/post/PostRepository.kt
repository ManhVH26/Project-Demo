package com.example.projectdemo.repository.post

import com.example.projectdemo.database.Post

interface PostRepository {
    suspend fun getAll(): List<Post>
    suspend fun getById(id: Long): Post?
    suspend fun getByUserId(userId: Long): List<Post>
    suspend fun getAllWithUser(): List<PostWithUser>
    suspend fun getByIdWithUser(id: Long): PostWithUser?
    suspend fun insert(title: String, content: String, userId: Long, createdAt: String): Long
    suspend fun update(id: Long, title: String, content: String, userId: Long)
    suspend fun delete(id: Long)
    suspend fun deleteByUserId(userId: Long)
}

data class PostWithUser(
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long,
    val createdAt: String,
    val userName: String,
    val userEmail: String
)
