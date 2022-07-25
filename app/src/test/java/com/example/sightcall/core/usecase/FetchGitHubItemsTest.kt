package com.example.sightcall.core.usecase

import com.example.sightcall.BaseUnitTest
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf


class FetchGitHubItemsTest: BaseUnitTest() {

    private lateinit var fetchGitHubItems: FetchGitHubItems

    @MockK
    private lateinit var remoteRepository: RemoteRepository

    @BeforeTest
    fun setUp() {
        fetchGitHubItems = FetchGitHubItems(remoteRepository)
        every {
            runBlocking {
                remoteRepository.githubItems(any())
            }
        } returns Either.Success(emptyList())
    }

    @Test
    fun fetchGitHubItemsRunIsSuccess() {
        val result = runBlocking {
            fetchGitHubItems.run("")
        }

        result shouldBeInstanceOf Either.Success::class.java
        (result as Either.Success).value shouldBeEqualTo emptyList()
        verify(exactly = 1) {
            runBlocking {
                remoteRepository.githubItems(any())
            }
        }
    }
}
