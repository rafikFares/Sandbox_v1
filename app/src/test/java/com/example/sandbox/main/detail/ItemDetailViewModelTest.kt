package com.example.sandbox.main.detail

import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.usecase.FetchGitHubItemDetailUseCase
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf


class ItemDetailViewModelTest : BaseAndroidTest() {

    @MockK
    private lateinit var fetchGitHubItemDetailUseCase: FetchGitHubItemDetailUseCase
    private lateinit var itemDetailViewModel: ItemDetailViewModel

    @BeforeTest
    fun setUp() {
        itemDetailViewModel = ItemDetailViewModel(fetchGitHubItemDetailUseCase)
    }

    @Test
    fun testLoadingState() {
        itemDetailViewModel.loadData(RemoteRepository.RepositoryInformation("test", "test"))

        itemDetailViewModel.uiState.value shouldBeInstanceOf ItemDetailViewModel.UiState.Loading::class.java
    }

    @Test
    fun testFetchDataSuccessUpdate() {
        val data = GitHubItemDetails.Empty
        coEvery {
            fetchGitHubItemDetailUseCase.run(any())
        } returns Either.Success(data)

        itemDetailViewModel.itemDetail.observeForever {
            it shouldBeEqualTo data
        }
    }

    @Test
    fun testFetchDataFailureUpdate() {
        val exception = SandboxException.NetworkConnectionException
        coEvery {
            fetchGitHubItemDetailUseCase.run(any())
        } returns Either.Failure(exception)

        itemDetailViewModel.failure.observeForever {
            it shouldBeEqualTo exception
        }
    }
}
