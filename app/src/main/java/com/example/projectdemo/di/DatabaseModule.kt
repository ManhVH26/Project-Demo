package com.example.projectdemo.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.projectdemo.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    
    // SQLDriver
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = androidContext(),
            name = "app_database.db"
        )
    }
    
    // AppDatabase
    single<AppDatabase> {
        AppDatabase(get())
    }
}
