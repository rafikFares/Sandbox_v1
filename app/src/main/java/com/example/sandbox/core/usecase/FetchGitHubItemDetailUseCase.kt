package com.example.sandbox.core.usecase

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.interactor.UseCase
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import org.koin.core.annotation.Single

@Single
class FetchGitHubItemDetailUseCase(private val remoteRepository: RemoteRepository):
    UseCase<GitHubItemDetails, RemoteRepository.RepositoryInformation>() {

    override suspend fun run(params: RemoteRepository.RepositoryInformation?): Either<SandboxException, GitHubItemDetails> {
        return params?.let {
            remoteRepository.retrieveItemDetailOf(params)
        } ?: Either.Failure(SandboxException.EmptyParamsException)
    }
}
