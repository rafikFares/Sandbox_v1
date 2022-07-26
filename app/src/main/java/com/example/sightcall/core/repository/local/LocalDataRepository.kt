package com.example.sightcall.core.repository.local

import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.repository.local.dao.ItemsDao
import com.example.sightcall.core.repository.local.entity.GitHubItemEntity
import com.example.sightcall.core.repository.local.entity.toGitHubItem
import com.example.sightcall.core.repository.local.entity.toItemEntity
import com.example.sightcall.core.utils.Either
import org.koin.core.annotation.Single

@Single
class LocalDataRepository(private val itemsDao: ItemsDao) : LocalRepository {

    override suspend fun insertItem(searchText: String?, gitHubItems: List<GitHubItem>): Either<SightCallException, Boolean> {
        return try {
            if (searchText.isNullOrBlank()) return Either.Failure(SightCallException.EmptyParamsException)

            val gitHubItemEntityTmp = GitHubItemEntity().apply {
                this.searchText = searchText
                this.items.addAll(gitHubItems.map { it.toItemEntity() })
            }
            itemsDao.insertItem(gitHubItemEntityTmp)
            Either.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Failure(SightCallException.DatabaseErrorException(e.message))
        }
    }

    override suspend fun retrieveItemsOf(searchText: String?): Either<SightCallException, List<GitHubItem>> {
        return try {
            if (searchText.isNullOrBlank()) return Either.Failure(SightCallException.EmptyParamsException)

            val gitHubItemEntityTmp = itemsDao.retrieveItem(searchText)
                ?: return Either.Failure(SightCallException.ElementNotFoundException(searchText))

            val items = gitHubItemEntityTmp.items.map { it.toGitHubItem() }
            Either.Success(items)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Failure(SightCallException.DatabaseErrorException(e.message))
        }
    }
}
