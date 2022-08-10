package com.example.sandbox.core.repository.local.dao

import com.example.sandbox.core.repository.local.entity.NodeEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.koin.core.annotation.Single

@Single
class ItemDaoImpl(private val realmDb: Realm) : ItemDao {

    override suspend fun insertItem(nodeEntity: NodeEntity) {
        realmDb.write {
            copyToRealm(nodeEntity, UpdatePolicy.ALL)
        }
    }

    override suspend fun retrieveItem(searchText: String): NodeEntity? {
        return realmDb.query<NodeEntity>("searchText == $0", searchText)
                .first().find()
    }

    override suspend fun retrieveAllItems(): List<NodeEntity> {
        val items = mutableListOf<NodeEntity>()
            items.addAll(
                realmDb.query<NodeEntity>()
                    .find()
            )
        return items
    }
}
