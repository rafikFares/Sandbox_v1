package com.example.sightcall.core.repository.local

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.utils.Either

interface LocalRepository {
    suspend fun insertItem(searchText: String?, gitHubItems: List<GitHubItem>): Either<SightCallException, Boolean>
    suspend fun retrieveItemsOf(searchText: String?): Either<SightCallException, List<GitHubItem>>
}
