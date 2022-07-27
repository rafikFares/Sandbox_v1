package com.example.sandbox.core.repository.remote

import com.example.sandbox.core.api.ServiceApi
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.platform.NetworkHandler
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class RemoteRepositoryImpl(
    private val serviceApi: ServiceApi,
    private val networkHandler: NetworkHandler,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : RemoteRepository {

    override suspend fun retrieveItems(params: String?): Either<SandboxException, List<GitHubItem>> =
        withContext(ioDispatcher) {
            if (networkHandler.isNetworkAvailable()) {
                try {
                    if (params.isNullOrBlank()) return@withContext Either.Failure(SandboxException.EmptyParamsException)

                    val response = serviceApi.fetchRepositories(params)
                    if (response.isSuccessful) {
                        val bodyData = response.body()?.items ?: emptyList()
                        val result = bodyData.map {
                            it.toGitHubItem()
                        }
                        return@withContext Either.Success(result)
                    }
                    return@withContext Either.Failure(SandboxException.ServerErrorException())
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@withContext Either.Failure(SandboxException.ServerErrorException(e.message))
                }
            } else {
                return@withContext Either.Failure(SandboxException.NetworkConnectionException)
            }
        }

    override suspend fun retrieveItemDetailOf(params: RemoteRepository.RepositoryInformation): Either<SandboxException, GitHubItemDetails> =
        withContext(ioDispatcher) {
            if (networkHandler.isNetworkAvailable()) {
                try {
                    val response = serviceApi.fetchRepositoryOf(ownerName = params.ownerName, repoName = params.repoName)
                    if (response.isSuccessful) {
                        val bodyData = response.body()
                        val result = bodyData?.toGitHubRepository() ?: GitHubItemDetails.Empty

                        return@withContext Either.Success(result)
                    }
                    return@withContext Either.Failure(SandboxException.ServerErrorException())
                } catch (e: Exception) {
                    return@withContext Either.Failure(SandboxException.ServerErrorException(e.message))
                }
            } else {
                return@withContext Either.Failure(SandboxException.NetworkConnectionException)
            }
        }
}
