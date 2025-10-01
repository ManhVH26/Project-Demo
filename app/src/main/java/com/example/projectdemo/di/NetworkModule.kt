package com.example.projectdemo.di

import com.example.projectdemo.data.remote.api.AuthApiService
import com.example.projectdemo.data.remote.api.AuthApiServiceImpl
import com.example.projectdemo.data.remote.api.CategoryApiService
import com.example.projectdemo.data.remote.api.CategoryApiServiceImpl
import com.example.projectdemo.data.remote.client.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    
    // HttpClientFactory
    single { HttpClientFactory(get()) }
    
    // HTTP Clients
    single<HttpClient>(named("movieClient")) {
        get<HttpClientFactory>().createMovieClient()
    }
    
    single<HttpClient>(named("authClient")) {
        get<HttpClientFactory>().createAuthClient()
    }
    
    // API Services
    single<AuthApiService> { AuthApiServiceImpl(get(named("authClient"))) }
    single<CategoryApiService> { CategoryApiServiceImpl(get(named("movieClient"))) }
}
