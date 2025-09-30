package com.example.projectdemo.di

import com.example.projectdemo.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}