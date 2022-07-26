package com.example.sightcall.core.repository.local.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class GitHubItemEntity : RealmObject {
    @PrimaryKey
    var id: String = ObjectId.create().toString()
    var searchText: String = ""
    var items: RealmList<ItemEntity> = realmListOf()
}
