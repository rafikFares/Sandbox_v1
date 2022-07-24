package com.example.sightcall.core.repository.remote

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.repository.data.GitHubItemDetails
import com.example.sightcall.core.utils.Either

interface RemoteRepository {

    data class RepositoryInformation(
        val ownerName: String,
        val repoName: String
    )

    suspend fun githubItems(params: String? = null): Either<SightCallException, List<GitHubItem>>
    suspend fun githubItemDetails(params: RepositoryInformation): Either<SightCallException, GitHubItemDetails>
}
