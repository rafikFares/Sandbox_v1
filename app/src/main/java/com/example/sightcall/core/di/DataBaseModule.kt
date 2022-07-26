package com.example.sightcall.core.di

import com.example.sightcall.core.database.newMigration
import com.example.sightcall.core.repository.local.entity.GitHubItemEntity
import com.example.sightcall.core.repository.local.entity.ItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

private const val realmVersion = 1L

val dataBaseModule = module {

    single<RealmConfiguration> {
        RealmConfiguration
            .Builder(
                schema = setOf(
                    GitHubItemEntity::class,
                    ItemEntity::class,
                )
            )
            .schemaVersion(realmVersion)
            .migration(newMigration)
            .build()
    }

    single<Realm> {
        val realmConfig: RealmConfiguration = get()
        Realm.open(realmConfig)
    }
}
