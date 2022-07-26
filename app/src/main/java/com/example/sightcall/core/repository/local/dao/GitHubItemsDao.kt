package com.example.sightcall.core.repository.local.dao

import com.example.sightcall.core.repository.local.entity.GitHubItemEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.koin.core.annotation.Single

@Single
class GitHubItemsDao(private val realmDb: Realm) : ItemsDao {

    override suspend fun insertItem(gitHubItemEntity: GitHubItemEntity) {
        realmDb.write {
            copyToRealm(gitHubItemEntity, UpdatePolicy.ALL)
        }
    }

    override suspend fun retrieveItem(searchText: String): GitHubItemEntity? {
        return realmDb.write {
            query<GitHubItemEntity>("searchText == $0", searchText)
                .first().find()
        }
    }

    override suspend fun retrieveAllItems(): List<GitHubItemEntity> {
        val items = mutableListOf<GitHubItemEntity>()
        realmDb.write {
            items.addAll(
                query<GitHubItemEntity>()
                    .find()
            )
        }
        return items
    }
}
