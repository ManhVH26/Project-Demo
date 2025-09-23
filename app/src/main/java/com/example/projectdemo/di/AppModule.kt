package com.example.projectdemo.di

import android.content.Context
import android.content.SharedPreferences
import com.example.projectdemo.core.configuration.AppRemoteConfiguration
import com.example.projectdemo.data.datasource.RemoteConfigDataSource
import com.example.projectdemo.data.datasource.RemoteConfigDataSourceImpl
import com.example.projectdemo.data.keys.RemoteKey
import com.example.projectdemo.data.repository.RemoteConfigRepositoryImpl
import com.example.projectdemo.data.tracking.FirebaseTrackingManagerImpl
import com.example.projectdemo.domain.repository.RemoteConfigRepository
import com.example.projectdemo.domain.tracking.FirebaseTrackingManager
import com.example.projectdemo.domain.tracking.UserJourneyTracker
import com.example.projectdemo.presentation.main.MainViewModel
import com.example.projectdemo.presentation.splash.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<FirebaseRemoteConfig> {
        val defaultMap: Map<String, Any> = RemoteKey.getAllKeys().associate { key ->
            key.remoteKey to key.defaultValue
        }

        FirebaseRemoteConfig.getInstance().apply {
            val settings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            setConfigSettingsAsync(settings)
            setDefaultsAsync(defaultMap)
        }
    }

    single<SharedPreferences> {
        androidContext()
            .getSharedPreferences("app_remote_config", Context.MODE_PRIVATE)
    }

    single<AppRemoteConfiguration> { AppRemoteConfiguration(get()) }

    single<RemoteConfigDataSource> { RemoteConfigDataSourceImpl(get()) }

    single<RemoteConfigRepository> {
        RemoteConfigRepositoryImpl(
            remoteConfigDataSource = get(),
            appConfig = get()
        )
    }

    single<FirebaseAnalytics> { Firebase.analytics }

    single<FirebaseTrackingManager> {
        FirebaseTrackingManagerImpl(get())
    }

    single<UserJourneyTracker> {
        UserJourneyTracker(get())
    }

    viewModel {
        SplashViewModel(
            remoteConfigRepository = get(),
            userJourneyTracker = get()
        )
    }

    viewModel {
        MainViewModel(
            appRemoteConfig = get(),
            userJourneyTracker = get()
        )
    }
}