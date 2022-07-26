package com.example.sandbox

import com.example.sandbox.core.di.appModule
import com.example.sandbox.core.di.dataBaseModule
import com.example.sandbox.core.di.dispatcherModule
import com.example.sandbox.core.di.networkModule
import io.mockk.mockkClass
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.defaultModule
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule

class BaseKoinTest : KoinTest, BaseAndroidTest() {

    @get:Rule
    val koinTestRule: KoinTestRule
        get() {
            stopKoin()

            return KoinTestRule.create {
                androidContext(appContext)
                printLogger()
                modules(
                    dispatcherModule +
                        networkModule +
                        dataBaseModule +
                        defaultModule +
                        appModule
                )
            }
        }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz, relaxed = true)
    }
}
