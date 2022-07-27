package com.example.sandbox

import android.app.Application
import android.content.Context
import com.example.sandbox.core.di.appModule
import com.example.sandbox.core.di.dataBaseModule
import com.example.sandbox.core.di.dispatcherModule
import com.example.sandbox.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class SandboxApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this@SandboxApplication

        startKoin {
            androidLogger()
            androidContext(appContext)
            modules(
                dispatcherModule +
                    networkModule +
                    dataBaseModule +
                    appModule +
                    defaultModule
            )
        }
    }
}
