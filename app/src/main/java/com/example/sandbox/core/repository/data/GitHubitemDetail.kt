package com.example.sandbox.core.repository.data

import com.example.sandbox.core.utils.empty

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
        val Empty = GitHubItemDetails(
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
