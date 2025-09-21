package com.example.projectdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projectdemo.presentation.navigation.AppNavHost
import com.example.projectdemo.ui.theme.ProjectDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ProjectDemoTheme {
                AppNavHost()
            }
        }
    }
}


