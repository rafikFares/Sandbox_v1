package com.example.sandbox.core.repository.local.entity

import com.example.sandbox.core.repository.data.GitHubItem
import io.realm.kotlin.types.RealmObject

class ItemEntity : RealmObject {
    var name: String = ""
    var owner: String = ""
    var language: String = ""
    var avatarUrl: String = ""
    var stars: Int = 0
}

fun GitHubItem.toItemEntity(): ItemEntity = ItemEntity().apply {
    name = repositoryName
    owner = repositoryOwner
    language = repositoryLanguage
    avatarUrl = repositoryOwnerAvatarUrl
    stars = repositoryStars
}

fun ItemEntity.toGitHubItem(): GitHubItem = GitHubItem(
    repositoryName = name,
    repositoryOwner = owner,
    repositoryOwnerAvatarUrl = language,
    repositoryLanguage = avatarUrl,
    repositoryStars = stars
)
