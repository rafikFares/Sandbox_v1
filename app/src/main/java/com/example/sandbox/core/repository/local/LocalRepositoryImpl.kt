package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.NodeEntity
import com.example.sandbox.core.repository.local.entity.toGitHubItem
import com.example.sandbox.core.repository.local.entity.toItemEntity
import com.example.sandbox.core.utils.Either
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(private val itemDao: ItemDao) : LocalRepository {

    override suspend fun insertItem(searchText: String?, gitHubItems: List<GitHubItem>): Either<SandboxException, Boolean> {
        return try {
            if (searchText.isNullOrBlank()) return Either.Failure(SandboxException.EmptyParamsException)

            val nodeEntityTmp = NodeEntity().apply {
                this.searchText = searchText
                this.items.addAll(gitHubItems.map { it.toItemEntity() })
            }
            itemDao.insertItem(nodeEntityTmp)
            Either.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Failure(SandboxException.DatabaseErrorException(e.message))
        }
    }

    override suspend fun retrieveItemsOf(searchText: String?): Either<SandboxException, List<GitHubItem>> {
        return try {
            if (searchText.isNullOrBlank()) return Either.Failure(SandboxException.EmptyParamsException)

            val gitHubItemEntityTmp = itemDao.retrieveItem(searchText)
                ?: return Either.Failure(SandboxException.ElementNotFoundException(searchText))

            val items = gitHubItemEntityTmp.items.map { it.toGitHubItem() }
            Either.Success(items)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Failure(SandboxException.DatabaseErrorException(e.message))
        }
    }
}
