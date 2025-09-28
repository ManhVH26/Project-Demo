package com.example.projectdemo.ui.presentation.post

import com.example.projectdemo.base.MviContract
import com.example.projectdemo.database.User
import com.example.projectdemo.repository.post.PostWithUser

object PostContract {
    data class State(
        val posts: List<PostWithUser> = emptyList(),
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedUserId: Long? = null,
        // UI State
        val showAddDialog: Boolean = false,
        val showEditDialog: Boolean = false,
        val showFilterDialog: Boolean = false,
        val selectedPost: PostWithUser? = null,
        // Form State
        val addFormTitle: String = "",
        val addFormContent: String = "",
        val addFormUserId: Long = 0L,
        val editFormTitle: String = "",
        val editFormContent: String = "",
        val editFormUserId: Long = 0L
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadPosts : Intent()
        object LoadUsers : Intent()
        data class AddPost(val title: String, val content: String, val userId: Long) : Intent()
        data class UpdatePost(val id: Long, val title: String, val content: String, val userId: Long) : Intent()
        data class DeletePost(val id: Long) : Intent()
        data class FilterByUser(val userId: Long?) : Intent()
        // UI Intents
        object ShowAddDialog : Intent()
        object HideAddDialog : Intent()
        object ShowEditDialog : Intent()
        object HideEditDialog : Intent()
        object ShowFilterDialog : Intent()
        object HideFilterDialog : Intent()
        data class SelectPost(val post: PostWithUser) : Intent()
        data class UpdateAddForm(val title: String, val content: String, val userId: Long) : Intent()
        data class UpdateEditForm(val title: String, val content: String, val userId: Long) : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowMessage(val message: String) : Effect()
    }
}
