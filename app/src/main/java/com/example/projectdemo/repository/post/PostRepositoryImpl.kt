package com.example.projectdemo.repository.post

import com.example.projectdemo.database.AppDatabase
import com.example.projectdemo.database.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepositoryImpl(private val db: AppDatabase) : PostRepository {

    override suspend fun getAll(): List<Post> = withContext(Dispatchers.IO) {
        db.postQueries.selectAll().executeAsList()
    }

    override suspend fun getById(id: Long): Post? = withContext(Dispatchers.IO) {
        db.postQueries.selectById(id).executeAsOneOrNull()
    }

    override suspend fun getByUserId(userId: Long): List<Post> = withContext(Dispatchers.IO) {
        db.postQueries.selectByUserId(userId).executeAsList()
    }

    override suspend fun getAllWithUser(): List<PostWithUser> = withContext(Dispatchers.IO) {
        db.postQueries.selectAllWithUser().executeAsList().map { row ->
            PostWithUser(
                id = row.id,
                title = row.title,
                content = row.content,
                userId = row.userId,
                createdAt = row.createdAt,
                userName = row.name,
                userEmail = row.email
            )
        }
    }

    override suspend fun getByIdWithUser(id: Long): PostWithUser? = withContext(Dispatchers.IO) {
        db.postQueries.selectByIdWithUser(id).executeAsOneOrNull()?.let { row ->
            PostWithUser(
                id = row.id,
                title = row.title,
                content = row.content,
                userId = row.userId,
                createdAt = row.createdAt,
                userName = row.name,
                userEmail = row.email
            )
        }
    }

    override suspend fun insert(title: String, content: String, userId: Long, createdAt: String): Long =
        withContext(Dispatchers.IO) {
            db.postQueries.insertPost(title, content, userId, createdAt)
            // SQLDelight with AUTOINCREMENT returns the last inserted row ID
            db.postQueries.selectAll().executeAsList().lastOrNull()?.id ?: 0L
        }

    override suspend fun update(id: Long, title: String, content: String, userId: Long) =
        withContext(Dispatchers.IO) {
            db.postQueries.updatePost(title, content, userId, id)
        }

    override suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        db.postQueries.deletePost(id)
    }

    override suspend fun deleteByUserId(userId: Long) = withContext(Dispatchers.IO) {
        db.postQueries.deletePostsByUserId(userId)
    }
}