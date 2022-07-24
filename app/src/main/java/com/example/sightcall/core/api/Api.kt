package com.example.sightcall.core.api

import com.example.sightcall.core.repository.remote.data.ApiItem
import com.example.sightcall.core.repository.remote.data.ApiRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/repositories")
    suspend fun fetchRepositories(@Query("q") search: String = "text"): Response<List<ApiItem>>

    @GET("repos/{owner}/{repo}")
    suspend fun fetchRepositoryOf(
        @Path(value = "owner", encoded = true) ownerName: String,
        @Path(value = "repo", encoded = true) repoName: String
    ): Response<ApiRepository>
}

