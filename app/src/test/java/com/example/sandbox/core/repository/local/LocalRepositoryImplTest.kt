package com.example.sandbox.core.repository.local

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.NodeEntity
import com.example.sandbox.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert


class LocalRepositoryImplTest : BaseUnitTest() {

    @MockK
    private lateinit var itemDao: ItemDao

    private lateinit var localRepository: LocalRepository

    @BeforeTest
    fun before() {
        localRepository = LocalRepositoryImpl(itemDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertItemEmptyParamsException() = runTest {
        val result = localRepository.insertItem("", emptyList())
        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBe SandboxException.EmptyParamsException
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertItemSuccess() = runTest {
        val result = localRepository.insertItem("test", emptyList())
        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBe true
        verify(exactly = 1) {
            runBlocking {
                itemDao.insertItem(any())
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun retrieveItemEmptyParamsException() = runTest {
        val result = localRepository.retrieveItemsOf(null)
        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBe SandboxException.EmptyParamsException
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun retrieveItemNotFoundException() = runTest {
        val searchText = "test"
        every {
            runBlocking {
                itemDao.retrieveItem(any())
            }
        } returns null
        val result = localRepository.retrieveItemsOf(searchText)
        result shouldBeInstanceOf Either.Failure::class.java
        val exception = (result as Either.Failure).value
        Assert.assertEquals(exception, SandboxException.ElementNotFoundException(searchText))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun retrieveItemSuccess() = runTest {
        val searchText = "test"
        val nodeEntity = NodeEntity()
        every {
            runBlocking {
                itemDao.retrieveItem(any())
            }
        } returns nodeEntity
        val result = localRepository.retrieveItemsOf(searchText)
        result shouldBeInstanceOf Either.Success::class.java
        Assert.assertEquals((result as Either.Success).value, emptyList<GitHubItem>())
    }
}
