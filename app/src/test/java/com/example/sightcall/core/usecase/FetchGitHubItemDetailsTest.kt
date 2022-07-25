package com.example.sightcall.core.usecase

import com.example.sightcall.BaseUnitTest
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import kotlin.test.BeforeTest


class FetchGitHubItemDetailsTest: BaseUnitTest() {
    private lateinit var fetchGitHubItemDetails: FetchGitHubItemDetails

    @MockK
    private lateinit var remoteRepository: RemoteRepository

    private val params = RemoteRepository.RepositoryInformation("tmp", "tmp")

    @BeforeTest
    fun setUp() {
        fetchGitHubItemDetails = FetchGitHubItemDetails(remoteRepository)
        every {
            runBlocking {
                remoteRepository.githubItemDetails(params)
            }
        } returns Either.Failure(SightCallException.NetworkConnectionException)
    }

    @Test
    fun fetchGitHubItemDetailsRunIsFailure() {
        val result = runBlocking {
            fetchGitHubItemDetails.run(params)
        }

        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBeEqualTo SightCallException.NetworkConnectionException
        verify(exactly = 1) {
            runBlocking {
                remoteRepository.githubItemDetails(any())
            }
        }
    }
}
