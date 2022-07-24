package com.example.sightcall.core.usecase

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.interactor.UseCase
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.utils.Either
import org.koin.core.annotation.Single

@Single
class FetchGitHubItems(private val remoteRepository: RemoteRepository): UseCase<List<GitHubItem>, String>() {

    override suspend fun run(params: String?): Either<SightCallException, List<GitHubItem>> =
        remoteRepository.githubItems(params)
}
