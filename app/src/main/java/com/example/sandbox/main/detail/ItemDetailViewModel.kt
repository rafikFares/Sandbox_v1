package com.example.sandbox.main.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.usecase.FetchGitHubItemDetailUseCase
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [ItemDetailViewModel::class])
class ItemDetailViewModel(
    private val fetchGitHubItemDetailUseCase: FetchGitHubItemDetailUseCase
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Loading : UiState
        data class UpdateData(val gitHubItemDetails: GitHubItemDetails) : UiState
        data class Error(val exception: SandboxException) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    private val _itemDetail = MutableLiveData<GitHubItemDetails>()
    val itemDetail: LiveData<GitHubItemDetails> = _itemDetail

    fun loadData(information: RemoteRepository.RepositoryInformation) {
        updateUiState(UiState.Loading)
        fetchGitHubItemDetailUseCase(information, viewModelScope) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleFailure(failure: SandboxException) {
        super.handleFailure(failure)
        updateUiState(UiState.Error(failure))
    }

    private fun handleSuccess(successData: GitHubItemDetails) {
        log("handleSuccess count : $successData")
        _itemDetail.value = successData
        updateUiState(UiState.UpdateData(successData))
    }

    private fun updateUiState(newState: UiState) {
        viewModelScope.launch {
            _uiState.value = newState
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemDetailViewModel", message, exception)
        }
    }
}
