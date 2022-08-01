package com.example.sandbox.main.home

import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.usecase.FetchGitHubItemsUseCase
import com.example.sandbox.core.utils.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

class HomeViewModelTest : BaseAndroidTest() {

    @MockK
    private lateinit var fetchGitHubItemsUseCase: FetchGitHubItemsUseCase
    private lateinit var homeViewModel: HomeViewModel

    @BeforeTest
    fun setUp() {
        homeViewModel = HomeViewModel(fetchGitHubItemsUseCase)
    }

    @Test
    fun testItemClick() {
        val item = GitHubItem(
            "",
            "",
            "",
            "",
            0
        )
        homeViewModel.handleItemClick(item)

        val uiState = homeViewModel.uiState.value

        uiState shouldBeEqualTo HomeViewModel.UiState.ItemClick(item)
    }

    @Test
    fun testLoadingState() {
        homeViewModel.searchAction("")

        val uiState = homeViewModel.uiState.value

        uiState shouldBe HomeViewModel.UiState.Loading
    }

    @Test
    fun testHandleSuccessAndUpdateItemsLiveData() {
        val item = GitHubItem(
            "",
            "",
            "",
            "",
            0
        )
        val resultList = listOf<GitHubItem>(item)
        coEvery {
            fetchGitHubItemsUseCase.run(any())
        } returns Either.Success(resultList)

        homeViewModel.gitHubItems.observeForever {
            it shouldBe resultList
        }

        homeViewModel.searchAction("")
    }

    @Test
    fun testHandleFailureAndUpdateFailureLiveData() {
        val exception = SandboxException.NetworkConnectionException
        coEvery {
            fetchGitHubItemsUseCase.run(any())
        } returns Either.Failure(exception)

        homeViewModel.failure.observeForever {
            it shouldBeInstanceOf SandboxException::class.java
            it shouldBeEqualTo exception
        }

        homeViewModel.searchAction("")
    }
}
