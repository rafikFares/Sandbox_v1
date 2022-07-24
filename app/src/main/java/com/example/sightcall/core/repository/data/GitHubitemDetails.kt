package com.example.sightcall.core.repository.data

data class GitHubItemDetails(
    val repositoryName: String,
    val repositoryOwner: String,
    val repositoryOwnerAvatarUrl: String,
    val repositoryLanguage: String,
    val repositorySshUrl: String,
    val repositoryDefaultBranch: String,
    val repositoryLicence: String?
) {
    companion object {
        val empty = GitHubItemDetails(
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty()
        )
    }
}

fun String.Companion.empty() = ""
