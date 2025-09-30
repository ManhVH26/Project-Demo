package com.example.projectdemo.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    
    // Handle effects
    LaunchedEffect(effect) {
        effect?.let { currentEffect ->
            when (currentEffect) {
                is MainContract.Effect.ShowMessage -> {
                    // Handle show message effect
                }
                is MainContract.Effect.NavigateToNext -> {
                    // Handle navigation effect
                }
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Main Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = uiState.data.ifEmpty { "No data loaded yet" },
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = uiState.userText,
            onValueChange = { viewModel.processIntent(MainContract.Intent.UpdateText(it)) },
            label = { Text("Enter text") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.processIntent(MainContract.Intent.LoadData) }
            ) {
                Text("Load Data")
            }
            
            Button(
                onClick = { 
                    viewModel.processIntent(MainContract.Intent.ClearText)
                }
            ) {
                Text("Clear Text")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (uiState.userText.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "User Text: ${uiState.userText}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
