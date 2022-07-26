package com.example.sightcall.core.usecase

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.interactor.UseCase
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.repository.local.LocalRepository
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.utils.Either
import com.example.sightcall.core.utils.ifIsFailureWithThan
import com.example.sightcall.core.utils.ifIsSuccessThan
import org.koin.core.annotation.Single

@Single
class FetchGitHubItems(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : UseCase<List<GitHubItem>, String>() {

    override suspend fun run(params: String?): Either<SightCallException, List<GitHubItem>> {
        val result = remoteRepository.githubItems(params)

        // if there is no network than we will check in local database
        result.ifIsFailureWithThan(SightCallException.NetworkConnectionException) {
            return localRepository.retrieveItemsOf(params)
        }
        // if the request was a success than we save data into local database
        result.ifIsSuccessThan {
            localRepository.insertItem(params, it)
        }
        // return success data or any other exception
        return result
    }
}
