package com.example.projectdemo.ui.theme

import androidx.compose.runtime.Composable
import com.example.projectdemo.ui.theme.colors.CustomColorScheme

object AppTheme {
    val colors: CustomColorScheme
        @Composable
        get() = LocalCustomColorScheme.current
}
