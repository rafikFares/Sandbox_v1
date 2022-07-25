package com.example.sightcall

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(
    application = SightCallApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O]
)
abstract class BaseAndroidTest: BaseTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = create(this@BaseAndroidTest)

    val appContext: Context
        get() = ApplicationProvider.getApplicationContext()
}
