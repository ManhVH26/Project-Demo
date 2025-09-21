package com.example.projectdemo.application

import android.app.Application
import com.example.projectdemo.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@ProjectApplication)
            modules(appModule)
        }
    }
}