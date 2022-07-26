package com.example.sandbox.core.di

import com.example.sandbox.core.database.automaticSchemaMigration
import com.example.sandbox.core.repository.local.entity.NodeEntity
import com.example.sandbox.core.repository.local.entity.ItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

private const val REALM_DB_VERSION = 1L

val dataBaseModule = module {

    single<RealmConfiguration> {
        RealmConfiguration
            .Builder(
                schema = setOf(
                    NodeEntity::class,
                    ItemEntity::class,
                )
            )
            .schemaVersion(REALM_DB_VERSION)
            .migration(automaticSchemaMigration)
            .build()
    }

    single<Realm> {
        val realmConfig: RealmConfiguration = get()
        Realm.open(realmConfig)
    }
}
