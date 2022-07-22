package com.example.sightcall

import android.app.Application
import android.content.Context
import com.example.sightcall.core.di.appModule
import com.example.sightcall.core.di.dispatcherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SightCallApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this@SightCallApplication

        startKoin {
            androidLogger()
            androidContext(appContext)
            modules(dispatcherModule + appModule)
        }

    }
}
