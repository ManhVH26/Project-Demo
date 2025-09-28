package com.example.projectdemo.repository.user

import com.example.projectdemo.database.User

interface UserRepository {
    suspend fun getAll(): List<User>
    suspend fun getById(id: Long): User?
    suspend fun insert(name: String, email: String): Long
    suspend fun update(id: Long, name: String, email: String)
    suspend fun delete(id: Long)
}