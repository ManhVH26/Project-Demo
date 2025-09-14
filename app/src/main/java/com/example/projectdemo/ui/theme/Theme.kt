package com.example.projectdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun ProjectDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ProvideCustomColorScheme(darkTheme = darkTheme) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}
