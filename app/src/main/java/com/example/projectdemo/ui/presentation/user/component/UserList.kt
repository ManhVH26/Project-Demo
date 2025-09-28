package com.example.projectdemo.ui.presentation.user.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectdemo.ui.presentation.user.UserContract
import com.example.projectdemo.ui.presentation.user.UserViewModel

@Composable
fun UserList(
    state: UserContract.State,
    viewModel: UserViewModel
) {
    if (state.users.isEmpty() && !state.isLoading) {
        // Empty state
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "👥",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No users found",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add your first user to get started",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.processIntent(UserContract.Intent.ShowAddDialog) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add User")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add First User")
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.users) { user ->
                UserItem(
                    user = user,
                    onEditClick = {
                        viewModel.processIntent(UserContract.Intent.SelectUser(user))
                        viewModel.processIntent(UserContract.Intent.ShowEditDialog)
                    },
                    onDeleteClick = {
                        viewModel.processIntent(UserContract.Intent.DeleteUser(user.id))
                    }
                )
            }
        }
    }
}