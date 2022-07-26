package com.example.sandbox.core.repository.remote.data

import com.example.sandbox.core.repository.data.GitHubItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGitHubItems( // list wrapper
    val items: List<ApiGitHubItem> = emptyList()
)

@Serializable
data class ApiGitHubItem(
    val name: String = "",
    val owner: ApiGitHubOwner,
    val language: String = "",
    @SerialName("stargazers_count")
    val stars: Int = 0
) {
    fun toGitHubItem(): GitHubItem = GitHubItem(
        repositoryName = name,
        repositoryOwner = owner.login,
        repositoryOwnerAvatarUrl = owner.avatarUrl,
        repositoryLanguage = language,
        repositoryStars = stars
    )
}
