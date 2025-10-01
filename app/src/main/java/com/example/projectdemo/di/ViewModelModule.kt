package com.example.projectdemo.di

import com.example.projectdemo.presentation.category.CategoryViewModel
import com.example.projectdemo.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    
    // ViewModels
    viewModel { MainViewModel(get(), get()) }
    viewModel { CategoryViewModel(get()) }
}
