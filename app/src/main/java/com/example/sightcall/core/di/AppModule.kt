package com.example.sightcall.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module


private const val SIGHTCALL_PREFERENCES = "sightCallPreferences"

val appModule = module {

    single<DataStore<Preferences>> {
        providePreferencesDataStore(
            get(),
            get(named("Dispatcher.IO"))
        )
    }

}

private fun providePreferencesDataStore(
    appContext: Context,
    ioDispatcher: CoroutineContext
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(appContext, SIGHTCALL_PREFERENCES)),
        scope = CoroutineScope(ioDispatcher + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(SIGHTCALL_PREFERENCES) }
    )
}
