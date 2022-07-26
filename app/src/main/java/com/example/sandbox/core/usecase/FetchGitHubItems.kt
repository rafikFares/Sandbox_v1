package com.example.sandbox.core.usecase

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.interactor.UseCase
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import com.example.sandbox.core.utils.ifIsFailureWithThan
import com.example.sandbox.core.utils.ifIsSuccessThan
import org.koin.core.annotation.Single

@Single
class FetchGitHubItems(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : UseCase<List<GitHubItem>, String>() {

    override suspend fun run(params: String?): Either<SandboxException, List<GitHubItem>> {
        val result = remoteRepository.retrieveItems(params)

        // if there is no network than we will check in local database
        result.ifIsFailureWithThan(SandboxException.NetworkConnectionException) {
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