package com.example.projectdemo.application

import android.app.Application
import com.example.projectdemo.di.dataStoreModule
import com.example.projectdemo.di.networkModule
import com.example.projectdemo.di.repositoryModule
import com.example.projectdemo.di.useCaseModule
import com.example.projectdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ProjectDemoApplication)
            modules(
                dataStoreModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}