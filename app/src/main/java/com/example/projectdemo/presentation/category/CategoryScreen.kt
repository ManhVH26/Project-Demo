package com.example.projectdemo.presentation.category

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectdemo.domain.model.Category
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    
    // Handle effects
    LaunchedEffect(effect) {
        when (effect) {
            is CategoryContract.Effect.ShowError -> {
                Log.e("CategoryScreen", "Error: ${uiState.error}")
                // Handle error - could show snackbar
            }
            is CategoryContract.Effect.NavigateToCategoryDetail -> {
                // Handle navigation to category detail
            }
            null -> {}
        }
    }
    
    // Load categories when screen is first displayed
    LaunchedEffect(Unit) {
        Log.d("CategoryScreen", "Loading categories...")
        viewModel.processIntent(CategoryContract.Intent.LoadCategories)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                Log.d("CategoryScreen", "Loading state: true")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Log.e("CategoryScreen", "Error state: ${uiState.error}")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { 
                                Log.d("CategoryScreen", "Retry button clicked")
                                viewModel.processIntent(CategoryContract.Intent.RefreshCategories) 
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            } else {
                Log.d("CategoryScreen", "Success state: ${uiState.categories.size} categories loaded")
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categories) { category ->
                        CategoryCard(
                            category = category,
                            onClick = {
                                Log.d("CategoryScreen", "Category clicked: ${category.name}")
                                viewModel.processIntent(
                                    CategoryContract.Intent.CategoryClicked(category)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
