package com.example.projectdemo.ui.presentation.user

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectdemo.ui.presentation.user.component.UserLoadingState
import com.example.projectdemo.ui.presentation.user.component.UserHeaderCard
import com.example.projectdemo.ui.presentation.user.component.UserList
import com.example.projectdemo.ui.presentation.user.dialog.AddUserDialog
import com.example.projectdemo.ui.presentation.user.dialog.EditUserDialog
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(effect) {
        effect?.let { effect ->
            when (effect) {
                is UserContract.Effect.ShowMessage -> {
                    Log.d("UserScreen", "Message: ${effect.message}")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UserHeaderCard(state, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            UserLoadingState()
        }

        UserList(state, viewModel)
    }

    if (state.showAddDialog) {
        AddUserDialog(
            name = state.addFormName,
            email = state.addFormEmail,
            onDismiss = { viewModel.processIntent(UserContract.Intent.HideAddDialog) },
            onNameChange = { name ->
                viewModel.processIntent(UserContract.Intent.UpdateAddForm(name, state.addFormEmail))
            },
            onEmailChange = { email ->
                viewModel.processIntent(UserContract.Intent.UpdateAddForm(state.addFormName, email))
            },
            onAddUser = { name, email ->
                viewModel.processIntent(UserContract.Intent.AddUser(name, email))
            }
        )
    }

    if (state.showEditDialog && state.selectedUser != null) {
        EditUserDialog(
            name = state.editFormName,
            email = state.editFormEmail,
            onDismiss = { viewModel.processIntent(UserContract.Intent.HideEditDialog) },
            onNameChange = { name ->
                viewModel.processIntent(
                    UserContract.Intent.UpdateEditForm(
                        name,
                        state.editFormEmail
                    )
                )
            },
            onEmailChange = { email ->
                viewModel.processIntent(
                    UserContract.Intent.UpdateEditForm(
                        state.editFormName,
                        email
                    )
                )
            },
            onUpdateUser = { name, email ->
                viewModel.processIntent(
                    UserContract.Intent.UpdateUser(
                        state.selectedUser!!.id,
                        name,
                        email
                    )
                )
            }
        )
    }
}







