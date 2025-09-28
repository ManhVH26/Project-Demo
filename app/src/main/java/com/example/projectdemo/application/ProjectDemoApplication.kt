package com.example.projectdemo.application

import android.app.Application
import com.example.projectdemo.di.appModule
import com.example.projectdemo.di.databaseModule
import com.example.projectdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ProjectDemoApplication)
            modules(
                appModule,
                databaseModule,
                viewModelModule
            )
        }
    }
}