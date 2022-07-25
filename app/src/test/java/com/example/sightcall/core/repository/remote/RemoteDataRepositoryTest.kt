package com.example.sightcall.core.repository.remote

import com.example.sightcall.BaseUnitTest
import com.example.sightcall.core.api.Api
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.platform.NetworkHandler
import com.example.sightcall.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf


class RemoteDataRepositoryTest : BaseUnitTest() {

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var api: Api

    private lateinit var remoteDataRepository: RemoteRepository

    @BeforeTest
    fun before() {
        remoteDataRepository = RemoteDataRepository(api, networkHandler)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteRepositoryShouldReturnNetworkException() = runTest {
        every { networkHandler.isNetworkAvailable() } returns false
        val result = remoteDataRepository.githubItems("")
        result shouldBeInstanceOf Either.Failure::class.java
        (result as Either.Failure).value shouldBe SightCallException.NetworkConnectionException
    }
}
