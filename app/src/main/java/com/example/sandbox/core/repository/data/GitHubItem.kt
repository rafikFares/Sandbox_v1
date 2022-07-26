package com.example.sandbox.core.repository.data

data class GitHubItem(
    val repositoryName: String,
    val repositoryOwner: String,
    val repositoryOwnerAvatarUrl: String,
    val repositoryLanguage: String,
    val repositoryStars: Int
)
