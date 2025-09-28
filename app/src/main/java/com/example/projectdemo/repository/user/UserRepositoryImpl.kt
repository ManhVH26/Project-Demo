package com.example.projectdemo.repository.user

import com.example.projectdemo.database.AppDatabase
import com.example.projectdemo.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val db: AppDatabase) : UserRepository {

    override suspend fun getAll(): List<User> = withContext(Dispatchers.IO) {
        db.userQueries.selectAll().executeAsList()
    }

    override suspend fun getById(id: Long): User? = withContext(Dispatchers.IO) {
        db.userQueries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun insert(name: String, email: String): Long = withContext(Dispatchers.IO) {
        db.userQueries.insertUser(name, email)
        // SQLDelight with AUTOINCREMENT returns the last inserted row ID
        db.userQueries.selectAll().executeAsList().lastOrNull()?.id ?: 0L
    }

    override suspend fun update(id: Long, name: String, email: String) =
        withContext(Dispatchers.IO) {
            db.userQueries.updateUser(name, email, id)
        }

    override suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        db.userQueries.deleteUser(id)
    }
}