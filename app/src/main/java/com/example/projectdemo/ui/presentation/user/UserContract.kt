package com.example.projectdemo.ui.presentation.user

import com.example.projectdemo.base.MviContract
import com.example.projectdemo.database.User

object UserContract {
    data class State(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        // UI State
        val showAddDialog: Boolean = false,
        val showEditDialog: Boolean = false,
        val selectedUser: User? = null,
        // Form State
        val addFormName: String = "",
        val addFormEmail: String = "",
        val editFormName: String = "",
        val editFormEmail: String = ""
    ) : MviContract.State

    sealed class Intent : MviContract.Intent {
        object LoadUsers : Intent()
        data class AddUser(val name: String, val email: String) : Intent()
        data class UpdateUser(val id: Long, val name: String, val email: String) : Intent()
        data class DeleteUser(val id: Long) : Intent()
        // UI Intents
        object ShowAddDialog : Intent()
        object HideAddDialog : Intent()
        object ShowEditDialog : Intent()
        object HideEditDialog : Intent()
        data class SelectUser(val user: User) : Intent()
        data class UpdateAddForm(val name: String, val email: String) : Intent()
        data class UpdateEditForm(val name: String, val email: String) : Intent()
    }

    sealed class Effect : MviContract.Effect {
        data class ShowMessage(val message: String) : Effect()
    }
}