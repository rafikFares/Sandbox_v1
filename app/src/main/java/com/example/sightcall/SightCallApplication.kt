package com.example.sightcall

import android.app.Application
import android.content.Context
import com.example.sightcall.core.di.appModule
import com.example.sightcall.core.di.dataBaseModule
import com.example.sightcall.core.di.dispatcherModule
import com.example.sightcall.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

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
            modules(
                dispatcherModule +
                    networkModule +
                    dataBaseModule +
                    defaultModule +
                    appModule

            )
        }
    }
}
