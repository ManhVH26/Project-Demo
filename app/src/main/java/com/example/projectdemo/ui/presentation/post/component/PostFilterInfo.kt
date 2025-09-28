package com.example.projectdemo.ui.presentation.post.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projectdemo.ui.presentation.post.PostContract
import com.example.projectdemo.ui.presentation.post.PostViewModel


@Composable
fun PostFilterInfo(
    state: PostContract.State,
    viewModel: PostViewModel
) {
    if (state.selectedUserId != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔍",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Filtered by: ${state.users.find { it.id == state.selectedUserId }?.name ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { viewModel.processIntent(PostContract.Intent.FilterByUser(null)) }
                ) {
                    Text("Clear Filter")
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}