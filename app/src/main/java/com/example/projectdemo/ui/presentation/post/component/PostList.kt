package com.example.projectdemo.ui.presentation.post.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.projectdemo.ui.presentation.post.PostContract
import com.example.projectdemo.ui.presentation.post.PostViewModel

@Composable
fun PostList(
    state: PostContract.State,
    viewModel: PostViewModel
) {
    if (state.posts.isEmpty() && !state.isLoading) {
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
                    text = "📝",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No posts found",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (state.selectedUserId != null) {
                        "No posts found for this user"
                    } else {
                        "Add your first post to get started"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Log.d("PostScreen", "Add First Post button clicked")
                        viewModel.processIntent(PostContract.Intent.ShowAddDialog)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Post")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add First Post")
                }
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.posts) { post ->
                PostCard(
                    post = post,
                    onEdit = {
                        viewModel.processIntent(PostContract.Intent.SelectPost(post))
                        viewModel.processIntent(PostContract.Intent.ShowEditDialog)
                    },
                    onDelete = {
                        viewModel.processIntent(PostContract.Intent.DeletePost(post.id))
                    }
                )
            }
        }
    }
}