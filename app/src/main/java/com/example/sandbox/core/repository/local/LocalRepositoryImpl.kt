package com.example.sandbox.core.repository.local

import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.NodeEntity
import com.example.sandbox.core.repository.local.entity.toGitHubItem
import com.example.sandbox.core.repository.local.entity.toItemEntity
import com.example.sandbox.core.utils.Either
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineContext
) : LocalRepository {

    override suspend fun insertItem(searchText: String?, gitHubItems: List<GitHubItem>): Either<SandboxException, Boolean> =
        withContext(ioDispatcher) {
            return@withContext try {
                if (searchText.isNullOrBlank()) return@withContext Either.Failure(SandboxException.EmptyParamsException)

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


    override suspend fun retrieveItemsOf(searchText: String?): Either<SandboxException, List<GitHubItem>> =
        withContext(ioDispatcher) {
            return@withContext try {
                if (searchText.isNullOrBlank()) return@withContext Either.Failure(SandboxException.EmptyParamsException)

                val gitHubItemEntityTmp = itemDao.retrieveItem(searchText)
                    ?: return@withContext Either.Failure(SandboxException.ElementNotFoundException(searchText))

                val items = gitHubItemEntityTmp.items.map { it.toGitHubItem() }
                Either.Success(items)
            } catch (e: Exception) {
                e.printStackTrace()
                Either.Failure(SandboxException.DatabaseErrorException(e.message))
            }
        }
}
