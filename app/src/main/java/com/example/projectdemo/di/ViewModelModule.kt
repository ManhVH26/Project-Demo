package com.example.projectdemo.di

import com.example.projectdemo.ui.presentation.user.UserViewModel
import com.example.projectdemo.ui.presentation.post.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    
    // UserViewModel
    viewModel {
        UserViewModel(get())
    }
    
    // PostViewModel
    viewModel {
        PostViewModel(get(), get())
    }
}
