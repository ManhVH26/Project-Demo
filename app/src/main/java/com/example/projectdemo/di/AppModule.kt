package com.example.projectdemo.di

import com.example.projectdemo.repository.user.UserRepository
import com.example.projectdemo.repository.user.UserRepositoryImpl
import com.example.projectdemo.repository.post.PostRepository
import com.example.projectdemo.repository.post.PostRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    // UserRepository
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    
    // PostRepository
    single<PostRepository> {
        PostRepositoryImpl(get())
    }
}
