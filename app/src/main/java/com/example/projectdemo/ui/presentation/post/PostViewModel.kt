package com.example.projectdemo.ui.presentation.post

import androidx.lifecycle.viewModelScope
import com.example.projectdemo.base.BaseViewModel
import com.example.projectdemo.repository.post.PostRepository
import com.example.projectdemo.repository.user.UserRepository
import com.example.projectdemo.repository.post.PostWithUser
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : BaseViewModel<PostContract.State, PostContract.Intent, PostContract.Effect>(
    initialState = PostContract.State()
) {

    override fun processIntent(intent: PostContract.Intent) {
        when (intent) {
            PostContract.Intent.LoadPosts -> loadPosts()
            PostContract.Intent.LoadUsers -> loadUsers()
            is PostContract.Intent.AddPost -> addPost(intent.title, intent.content, intent.userId)
            is PostContract.Intent.UpdatePost -> updatePost(intent.id, intent.title, intent.content, intent.userId)
            is PostContract.Intent.DeletePost -> deletePost(intent.id)
            is PostContract.Intent.FilterByUser -> filterByUser(intent.userId)
            // UI Intents
            PostContract.Intent.ShowAddDialog -> setState { 
                copy(
                    showAddDialog = true,
                    addFormUserId = users.firstOrNull()?.id ?: 0L
                ) 
            }
            PostContract.Intent.HideAddDialog -> setState { 
                copy(
                    showAddDialog = false,
                    addFormTitle = "",
                    addFormContent = "",
                    addFormUserId = 0L
                ) 
            }
            PostContract.Intent.ShowEditDialog -> setState { copy(showEditDialog = true) }
            PostContract.Intent.HideEditDialog -> setState { 
                copy(
                    showEditDialog = false,
                    selectedPost = null,
                    editFormTitle = "",
                    editFormContent = "",
                    editFormUserId = 0L
                ) 
            }
            PostContract.Intent.ShowFilterDialog -> setState { copy(showFilterDialog = true) }
            PostContract.Intent.HideFilterDialog -> setState { copy(showFilterDialog = false) }
            is PostContract.Intent.SelectPost -> setState { 
                copy(
                    selectedPost = intent.post,
                    editFormTitle = intent.post.title,
                    editFormContent = intent.post.content,
                    editFormUserId = intent.post.userId
                ) 
            }
            is PostContract.Intent.UpdateAddForm -> setState { 
                copy(
                    addFormTitle = intent.title,
                    addFormContent = intent.content,
                    addFormUserId = intent.userId
                ) 
            }
            is PostContract.Intent.UpdateEditForm -> setState { 
                copy(
                    editFormTitle = intent.title,
                    editFormContent = intent.content,
                    editFormUserId = intent.userId
                ) 
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                val posts = postRepository.getAllWithUser()
                setState { copy(posts = posts, isLoading = false) }
            } catch (e: Exception) {
                setState { copy(error = e.localizedMessage, isLoading = false) }
                setEffect { PostContract.Effect.ShowMessage("Load posts failed: ${e.localizedMessage}") }
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            try {
                val users = userRepository.getAll()
                setState { copy(users = users) }
            } catch (e: Exception) {
                setEffect { PostContract.Effect.ShowMessage("Load users failed: ${e.localizedMessage}") }
            }
        }
    }

    private fun addPost(title: String, content: String, userId: Long) {
        viewModelScope.launch {
            try {
                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                postRepository.insert(title, content, userId, currentTime)
                loadPosts()
                setState { 
                    copy(
                        showAddDialog = false,
                        addFormTitle = "",
                        addFormContent = "",
                        addFormUserId = 0L
                    ) 
                }
                setEffect { PostContract.Effect.ShowMessage("Post added successfully") }
            } catch (e: Exception) {
                setEffect { PostContract.Effect.ShowMessage("Failed to add post: ${e.localizedMessage}") }
            }
        }
    }

    private fun updatePost(id: Long, title: String, content: String, userId: Long) {
        viewModelScope.launch {
            try {
                postRepository.update(id, title, content, userId)
                loadPosts()
                setState { 
                    copy(
                        showEditDialog = false,
                        selectedPost = null,
                        editFormTitle = "",
                        editFormContent = "",
                        editFormUserId = 0L
                    ) 
                }
                setEffect { PostContract.Effect.ShowMessage("Post updated successfully") }
            } catch (e: Exception) {
                setEffect { PostContract.Effect.ShowMessage("Failed to update post: ${e.localizedMessage}") }
            }
        }
    }

    private fun deletePost(id: Long) {
        viewModelScope.launch {
            try {
                postRepository.delete(id)
                loadPosts()
                setEffect { PostContract.Effect.ShowMessage("Post deleted successfully") }
            } catch (e: Exception) {
                setEffect { PostContract.Effect.ShowMessage("Failed to delete post: ${e.localizedMessage}") }
            }
        }
    }

    private fun filterByUser(userId: Long?) {
        setState { copy(selectedUserId = userId) }
        if (userId != null) {
            viewModelScope.launch {
                setState { copy(isLoading = true, error = null) }
                try {
                    val posts = postRepository.getByUserId(userId).map { post ->
                        val user = userRepository.getById(post.userId)
                        PostWithUser(
                            id = post.id,
                            title = post.title,
                            content = post.content,
                            userId = post.userId,
                            createdAt = post.createdAt,
                            userName = user?.name ?: "Unknown",
                            userEmail = user?.email ?: "Unknown"
                        )
                    }
                    setState { copy(posts = posts, isLoading = false) }
                } catch (e: Exception) {
                    setState { copy(error = e.localizedMessage, isLoading = false) }
                    setEffect { PostContract.Effect.ShowMessage("Filter failed: ${e.localizedMessage}") }
                }
            }
        } else {
            loadPosts()
        }
    }
}
