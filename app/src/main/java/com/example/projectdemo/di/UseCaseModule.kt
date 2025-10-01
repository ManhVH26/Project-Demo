package com.example.projectdemo.di

import com.example.projectdemo.domain.usecase.GetCategoriesUseCase
import com.example.projectdemo.domain.usecase.SilentLoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    
    // Use Cases
    single { GetCategoriesUseCase(get()) }
    single { SilentLoginUseCase(get()) }
}
