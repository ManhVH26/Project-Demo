package com.example.projectdemo.ui.presentation.post.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.projectdemo.database.User

@Composable
fun FilterDialog(
    users: List<User>,
    selectedUserId: Long?,
    onDismiss: () -> Unit,
    onConfirm: (Long?) -> Unit
) {
    var currentSelection by remember { mutableStateOf(selectedUserId) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Posts by User") },
        text = {
            Column {
                TextButton(
                    onClick = { currentSelection = null },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show All Posts")
                }
                users.forEach { user ->
                    TextButton(
                        onClick = { currentSelection = user.id },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(user.name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(currentSelection) }
            ) {
                Text("Apply Filter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
