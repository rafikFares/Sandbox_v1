package com.example.sandbox.core.api

import com.example.sandbox.core.repository.remote.data.ApiGitHubItemDetail
import com.example.sandbox.core.repository.remote.data.ApiGitHubItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("search/repositories")
    suspend fun fetchRepositories(@Query("q") search: String): Response<ApiGitHubItems>

    @GET("repos/{owner}/{repo}")
    suspend fun fetchRepositoryOf(
        @Path(value = "owner", encoded = true) ownerName: String,
        @Path(value = "repo", encoded = true) repoName: String
    ): Response<ApiGitHubItemDetail>
}

