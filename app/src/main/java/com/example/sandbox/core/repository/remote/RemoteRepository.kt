package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.utils.Either

interface RemoteRepository {

    data class RepositoryInformation(
        val ownerName: String,
        val repoName: String
    )

    suspend fun retrieveItems(params: String? = null): Either<SandboxException, List<GitHubItem>>
    suspend fun retrieveItemDetailOf(params: RepositoryInformation): Either<SandboxException, GitHubItemDetails>
}
