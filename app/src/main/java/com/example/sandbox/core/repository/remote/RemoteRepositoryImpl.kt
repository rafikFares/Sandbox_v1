package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.api.ServiceApi
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.platform.NetworkHandler
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.utils.Either
import org.koin.core.annotation.Single

@Single
class RemoteRepositoryImpl(
    private val serviceApi: ServiceApi,
    private val networkHandler: NetworkHandler,
): RemoteRepository {

    override suspend fun retrieveItems(params: String?): Either<SandboxException, List<GitHubItem>> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val response = serviceApi.fetchRepositories(params ?: "text") // for the moment i don't return "EmptyParamsException" but i just use "text" as default
                if (response.isSuccessful) {
                    val bodyData = response.body()?.items ?: emptyList()
                    val result = bodyData.map {
                        it.toGitHubItem()
                    }
                    return Either.Success(result)
                }
                return Either.Failure(SandboxException.ServerErrorException())
            } catch (e: Exception) {
                e.printStackTrace()
                return Either.Failure(SandboxException.ServerErrorException(e.message))
            }
        } else {
            return Either.Failure(SandboxException.NetworkConnectionException)
        }
    }

    override suspend fun retrieveItemDetailOf(params: RemoteRepository.RepositoryInformation): Either<SandboxException, GitHubItemDetails> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val response = serviceApi.fetchRepositoryOf(ownerName = params.ownerName, repoName = params.repoName)
                if (response.isSuccessful) {
                    val bodyData = response.body()
                    val result =  bodyData?.toGitHubRepository() ?: GitHubItemDetails.Empty

                    return Either.Success(result)
                }
                return Either.Failure(SandboxException.ServerErrorException())
            } catch (e: Exception) {
                return Either.Failure(SandboxException.ServerErrorException(e.message))
            }
        } else {
            return Either.Failure(SandboxException.NetworkConnectionException)
        }
    }
}
