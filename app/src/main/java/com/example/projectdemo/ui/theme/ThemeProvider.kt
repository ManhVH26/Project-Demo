package com.example.projectdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.projectdemo.ui.theme.colors.CustomColorScheme
import com.example.projectdemo.ui.theme.colors.DarkColorScheme
import com.example.projectdemo.ui.theme.colors.LightColorScheme

val LocalCustomColorScheme = staticCompositionLocalOf<CustomColorScheme> {
    error("CustomColorScheme not provided")
}

@Composable
fun ProvideCustomColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    
    CompositionLocalProvider(LocalCustomColorScheme provides colorScheme) {
        content()
    }
}

