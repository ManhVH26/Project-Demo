package com.example.projectdemo.ui.presentation.user

import androidx.lifecycle.viewModelScope
import com.example.projectdemo.base.BaseViewModel
import com.example.projectdemo.repository.user.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : BaseViewModel<UserContract.State, UserContract.Intent, UserContract.Effect>(
    initialState = UserContract.State()
) {

    override fun processIntent(intent: UserContract.Intent) {
        when (intent) {
            UserContract.Intent.LoadUsers -> loadUsers()
            is UserContract.Intent.AddUser -> addUser(intent.name, intent.email)
            is UserContract.Intent.UpdateUser -> updateUser(intent.id, intent.name, intent.email)
            is UserContract.Intent.DeleteUser -> deleteUser(intent.id)
            // UI Intents
            UserContract.Intent.ShowAddDialog -> setState { copy(showAddDialog = true) }
            UserContract.Intent.HideAddDialog -> setState { 
                copy(
                    showAddDialog = false,
                    addFormName = "",
                    addFormEmail = ""
                ) 
            }
            UserContract.Intent.ShowEditDialog -> setState { copy(showEditDialog = true) }
            UserContract.Intent.HideEditDialog -> setState { 
                copy(
                    showEditDialog = false,
                    selectedUser = null,
                    editFormName = "",
                    editFormEmail = ""
                ) 
            }
            is UserContract.Intent.SelectUser -> setState { 
                copy(
                    selectedUser = intent.user,
                    editFormName = intent.user.name,
                    editFormEmail = intent.user.email
                ) 
            }
            is UserContract.Intent.UpdateAddForm -> setState { 
                copy(
                    addFormName = intent.name,
                    addFormEmail = intent.email
                ) 
            }
            is UserContract.Intent.UpdateEditForm -> setState { 
                copy(
                    editFormName = intent.name,
                    editFormEmail = intent.email
                ) 
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            try {
                val users = repository.getAll()
                setState { copy(users = users, isLoading = false) }
            } catch (e: Exception) {
                setState { copy(error = e.localizedMessage, isLoading = false) }
                setEffect { UserContract.Effect.ShowMessage("Load failed: ${e.localizedMessage}") }
            }
        }
    }

    private fun addUser(name: String, email: String) {
        viewModelScope.launch {
            try {
                repository.insert(name, email)
                loadUsers()
                setState { copy(showAddDialog = false, addFormName = "", addFormEmail = "") }
                setEffect { UserContract.Effect.ShowMessage("User added successfully") }
            } catch (e: Exception) {
                setEffect { UserContract.Effect.ShowMessage("Failed to add user: ${e.localizedMessage}") }
            }
        }
    }

    private fun updateUser(id: Long, name: String, email: String) {
        viewModelScope.launch {
            try {
                repository.update(id, name, email)
                loadUsers()
                setState { 
                    copy(
                        showEditDialog = false, 
                        selectedUser = null,
                        editFormName = "",
                        editFormEmail = ""
                    ) 
                }
                setEffect { UserContract.Effect.ShowMessage("User updated successfully") }
            } catch (e: Exception) {
                setEffect { UserContract.Effect.ShowMessage("Failed to update user: ${e.localizedMessage}") }
            }
        }
    }

    private fun deleteUser(id: Long) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadUsers()
                setEffect { UserContract.Effect.ShowMessage("User deleted successfully") }
            } catch (e: Exception) {
                setEffect { UserContract.Effect.ShowMessage("Failed to delete user: ${e.localizedMessage}") }
            }
        }
    }
}