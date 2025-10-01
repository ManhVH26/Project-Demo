package com.example.projectdemo.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectdemo.presentation.category.CategoryScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    var showCategoryScreen by remember { mutableStateOf(false) }
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Handle effects
    LaunchedEffect(effect) {
        effect?.let { currentEffect ->
            when (currentEffect) {
                is MainContract.Effect.LoginSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = currentEffect.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is MainContract.Effect.LoginError -> {
                    snackbarHostState.showSnackbar(
                        message = currentEffect.error,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }
    
    if (showCategoryScreen) {
        CategoryScreen(
            onNavigateBack = { showCategoryScreen = false }
        )
        return
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Silent Login",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Silent Login Button
            Button(
                onClick = { 
                    viewModel.processIntent(MainContract.Intent.SilentLogin)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Silent Login")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // View Categories Button - show when authenticated
            if (uiState.isAuthenticated) {
                Button(
                    onClick = { showCategoryScreen = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Categories")
                }
            }
            
            // Error Display
            if (uiState.authError != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Lỗi đăng nhập:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.authError ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}
