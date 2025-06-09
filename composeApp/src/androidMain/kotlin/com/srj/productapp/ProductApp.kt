package com.srj.productapp

import android.app.Application
import android.util.Log
import com.srj.productapp.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ProductApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("ProductApp", "!!!!!!!!! PRODUCT APP ONCREATE IS RUNNING !!!!!!!!!")

        initKoin {
            this.androidContext(this@ProductApp)
            androidLogger(org.koin.core.logger.Level.INFO)

        }
    }
}