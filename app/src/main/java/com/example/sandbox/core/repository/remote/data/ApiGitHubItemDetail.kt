package com.example.sandbox.core.repository.remote.data

import com.example.sandbox.core.repository.data.GitHubItemDetails
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGitHubItemDetail(
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("default_branch")
    val defaultBranch: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("ssh_url")
    val sshUrl: String = "",
    @SerialName("language")
    val language: String = "",
    @SerialName("full_name")
    val fullName: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("license")
    val apiGitHubLicense: ApiGitHubLicense? = null,
    @SerialName("owner")
    val owner: ApiGitHubOwner = ApiGitHubOwner(),
    @SerialName("forks_count")
    val forksCount: Int = 0
) {
    fun toGitHubRepository(): GitHubItemDetails = GitHubItemDetails(
        repositoryName = name,
        repositoryOwner = owner.login,
        repositoryOwnerAvatarUrl = owner.avatarUrl,
        repositoryLanguage = language,
        repositorySshUrl = sshUrl,
        repositoryDefaultBranch = defaultBranch,
        repositoryLicence = apiGitHubLicense?.spdxId
    )
}
