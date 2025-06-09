package com.srj.productapp.core.di


import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

// 'actual' implementation of the platformModule for Android
actual fun platformModule(): Module = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("your_app_prefs", Context.MODE_PRIVATE)
        // "your_app_prefs" should be a unique name for your app's preferences file
    }
    single<Settings> {
        SharedPreferencesSettings(get()) // 'get()' will provide the Android Context
    }
}