package com.example.sandbox.core.usecase

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import kotlin.test.BeforeTest


class FetchGitHubItemDetailTest: BaseUnitTest() {
    private lateinit var fetchGitHubItemDetail: FetchGitHubItemDetail

    @MockK
    private lateinit var remoteRepository: RemoteRepository

    private val params = RemoteRepository.RepositoryInformation("tmp", "tmp")

    @BeforeTest
    fun setUp() {
        fetchGitHubItemDetail = FetchGitHubItemDetail(remoteRepository)
        every {
            runBlocking {
                remoteRepository.retrieveItemDetailOf(params)
            }
        } returns Either.Failure(SandboxException.NetworkConnectionException)
    }

    @Test
    fun fetchGitHubItemDetailsRunIsFailure() {
        val result = runBlocking {
            fetchGitHubItemDetail.run(params)
        }

        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBeEqualTo SandboxException.NetworkConnectionException
        verify(exactly = 1) {
            runBlocking {
                remoteRepository.retrieveItemDetailOf(any())
            }
        }
    }
}
