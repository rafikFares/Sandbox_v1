package com.example.sandbox.core.usecase

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.repository.local.LocalRepository
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf


class FetchGitHubItemsTest: BaseUnitTest() {

    private lateinit var fetchGitHubItems: FetchGitHubItems

    @MockK
    private lateinit var remoteRepository: RemoteRepository
    @MockK
    private lateinit var localRepository: LocalRepository

    @BeforeTest
    fun setUp() {
        fetchGitHubItems = FetchGitHubItems(remoteRepository, localRepository)
        coEvery {
            remoteRepository.retrieveItems(any())
        } returns Either.Success(emptyList())
        coEvery {
            localRepository.insertItem(any(), any())
        } returns Either.Success(true)
    }

    @Test
    fun fetchGitHubItemsRunIsSuccess() {
        val result = runBlocking {
            fetchGitHubItems.run("")
        }

        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBeEqualTo emptyList()
        coVerify(exactly = 1) {
            remoteRepository.retrieveItems(any())
        }
    }
}
