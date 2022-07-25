package com.example.sightcall.core.repository.remote

import com.example.sightcall.core.api.Api
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.platform.NetworkHandler
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.repository.data.GitHubItemDetails
import com.example.sightcall.core.utils.Either
import org.koin.core.annotation.Single

@Single
class RemoteDataRepository(
    private val api: Api,
    private val networkHandler: NetworkHandler,
): RemoteRepository {

    override suspend fun githubItems(params: String?): Either<SightCallException, List<GitHubItem>> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val response = api.fetchRepositories(params ?: "text")
                if (response.isSuccessful) {
                    val bodyData = response.body()?.items ?: emptyList()
                    val result = bodyData.map {
                        it.toGitHubItem()
                    }
                    return Either.Success(result)
                }
                return Either.Failure(SightCallException.ServerErrorException())
            } catch (e: Exception) {
                e.printStackTrace()
                return Either.Failure(SightCallException.ServerErrorException(e.message))
            }
        } else {
            return Either.Failure(SightCallException.NetworkConnectionException)
        }
    }

    override suspend fun githubItemDetails(params: RemoteRepository.RepositoryInformation): Either<SightCallException, GitHubItemDetails> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val response = api.fetchRepositoryOf(ownerName = params.ownerName, repoName = params.repoName)
                if (response.isSuccessful) {
                    val bodyData = response.body()
                    val result =  bodyData?.toGitHubRepository() ?: GitHubItemDetails.empty

                    return Either.Success(result)
                }
                return Either.Failure(SightCallException.ServerErrorException())
            } catch (e: Exception) {
                return Either.Failure(SightCallException.ServerErrorException(e.message))
            }
        } else {
            return Either.Failure(SightCallException.NetworkConnectionException)
        }
    }
}
