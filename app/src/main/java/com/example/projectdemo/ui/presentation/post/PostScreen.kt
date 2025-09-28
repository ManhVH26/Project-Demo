package com.example.projectdemo.ui.presentation.post

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
import com.example.projectdemo.ui.presentation.post.component.PostFilterInfo
import com.example.projectdemo.ui.presentation.post.component.PostHeaderCard
import com.example.projectdemo.ui.presentation.post.component.PostList
import com.example.projectdemo.ui.presentation.post.component.PostLoadingState
import com.example.projectdemo.ui.presentation.post.dialog.AddPostDialog
import com.example.projectdemo.ui.presentation.post.dialog.EditPostDialog
import com.example.projectdemo.ui.presentation.post.dialog.FilterDialog
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    
    // Load initial data
    LaunchedEffect(Unit) {
        viewModel.processIntent(PostContract.Intent.LoadPosts)
        viewModel.processIntent(PostContract.Intent.LoadUsers)
    }
    
    // Handle effects
    LaunchedEffect(effect) {
        effect?.let { effect ->
            when (effect) {
                is PostContract.Effect.ShowMessage -> {
                    Log.d("PostScreen", "Message: ${effect.message}")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PostHeaderCard(state, viewModel)
        
        Spacer(modifier = Modifier.height(16.dp))

        PostFilterInfo(state, viewModel)

        PostLoadingState(state)

        PostList(state, viewModel)
    }
    
    if (state.showAddDialog) {
        AddPostDialog(
            users = state.users,
            title = state.addFormTitle,
            content = state.addFormContent,
            selectedUserId = state.addFormUserId,
            onDismiss = { viewModel.processIntent(PostContract.Intent.HideAddDialog) },
            onTitleChange = { title -> 
                viewModel.processIntent(PostContract.Intent.UpdateAddForm(title, state.addFormContent, state.addFormUserId))
            },
            onContentChange = { content -> 
                viewModel.processIntent(PostContract.Intent.UpdateAddForm(state.addFormTitle, content, state.addFormUserId))
            },
            onUserIdChange = { userId -> 
                viewModel.processIntent(PostContract.Intent.UpdateAddForm(state.addFormTitle, state.addFormContent, userId))
            },
            onConfirm = { title, content, userId ->
                viewModel.processIntent(PostContract.Intent.AddPost(title, content, userId))
            }
        )
    }
    
    if (state.showEditDialog && state.selectedPost != null) {
        EditPostDialog(
            users = state.users,
            title = state.editFormTitle,
            content = state.editFormContent,
            selectedUserId = state.editFormUserId,
            onDismiss = { viewModel.processIntent(PostContract.Intent.HideEditDialog) },
            onTitleChange = { title -> 
                viewModel.processIntent(PostContract.Intent.UpdateEditForm(title, state.editFormContent, state.editFormUserId))
            },
            onContentChange = { content -> 
                viewModel.processIntent(PostContract.Intent.UpdateEditForm(state.editFormTitle, content, state.editFormUserId))
            },
            onUserIdChange = { userId -> 
                viewModel.processIntent(PostContract.Intent.UpdateEditForm(state.editFormTitle, state.editFormContent, userId))
            },
            onConfirm = { title, content, userId ->
                viewModel.processIntent(PostContract.Intent.UpdatePost(state.selectedPost!!.id, title, content, userId))
            }
        )
    }
    
    if (state.showFilterDialog) {
        FilterDialog(
            users = state.users,
            selectedUserId = state.selectedUserId,
            onDismiss = { viewModel.processIntent(PostContract.Intent.HideFilterDialog) },
            onConfirm = { userId ->
                viewModel.processIntent(PostContract.Intent.FilterByUser(userId))
                viewModel.processIntent(PostContract.Intent.HideFilterDialog)
            }
        )
    }
}









