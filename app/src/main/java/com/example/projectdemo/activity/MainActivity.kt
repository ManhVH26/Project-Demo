package com.example.projectdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.projectdemo.presentation.main.MainScreen
import com.example.projectdemo.presentation.theme.ProjectDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectDemoTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }
}