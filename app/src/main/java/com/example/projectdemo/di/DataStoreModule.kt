package com.example.projectdemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.projectdemo.data.device.AndroidDeviceInfoProvider
import com.example.projectdemo.data.device.DeviceInfoProvider
import com.example.projectdemo.data.local.datastore.CredentialsDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Extension property for DataStore creation
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "credentials_prefs")

val dataStoreModule = module {
    
    // DataStore
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
    
    // CredentialsDataSource
    single { CredentialsDataSource(get()) }
    
    // DeviceInfoProvider
    single<DeviceInfoProvider> { AndroidDeviceInfoProvider(androidContext()) }
}

