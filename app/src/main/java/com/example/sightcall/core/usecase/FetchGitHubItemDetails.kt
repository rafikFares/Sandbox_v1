package com.example.sightcall.core.usecase

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.interactor.UseCase
import com.example.sightcall.core.repository.data.GitHubItemDetails
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.utils.Either
import org.koin.core.annotation.Single

@Single
class FetchGitHubItemDetails(private val remoteRepository: RemoteRepository):
    UseCase<GitHubItemDetails, RemoteRepository.RepositoryInformation>() {

    override suspend fun run(params: RemoteRepository.RepositoryInformation?): Either<SightCallException, GitHubItemDetails> {
        return params?.let {
            remoteRepository.githubItemDetails(params)
        } ?: Either.Failure(SightCallException.EmptyParamsException)
    }
}
