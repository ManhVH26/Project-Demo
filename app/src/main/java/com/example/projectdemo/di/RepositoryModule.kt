package com.example.projectdemo.di

import com.example.projectdemo.data.repository.AuthRepository
import com.example.projectdemo.data.repository.AuthRepositoryImpl
import com.example.projectdemo.data.repository.CategoryRepository
import com.example.projectdemo.data.repository.CategoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    
    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}
