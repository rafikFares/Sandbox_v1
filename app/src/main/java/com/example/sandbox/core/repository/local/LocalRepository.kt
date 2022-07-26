package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.utils.Either

interface LocalRepository {
    suspend fun insertItem(searchText: String?, gitHubItems: List<GitHubItem>): Either<SandboxException, Boolean>
    suspend fun retrieveItemsOf(searchText: String?): Either<SandboxException, List<GitHubItem>>
}
