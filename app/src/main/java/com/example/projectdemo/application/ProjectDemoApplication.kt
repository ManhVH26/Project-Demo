package com.example.projectdemo.application

import android.app.Application
import com.example.projectdemo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ProjectDemoApplication)
            modules(appModule)
        }
    }
}