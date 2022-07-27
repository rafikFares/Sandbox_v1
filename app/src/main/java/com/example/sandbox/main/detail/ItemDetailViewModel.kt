package com.example.sandbox.main.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.data.GitHubItemDetails
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.core.usecase.FetchGitHubItemDetail
import com.example.sandbox.main.platform.BaseViewModel
import com.example.sandbox.main.start.StartViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [ItemDetailViewModel::class])
class ItemDetailViewModel(
    private val fetchGitHubItemDetail: FetchGitHubItemDetail
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Loading : UiState
        data class UpdateData(val gitHubItemDetails: GitHubItemDetails) : UiState
        data class Error(val exception: SandboxException) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    fun loadData(information: RemoteRepository.RepositoryInformation) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
        }
        fetchGitHubItemDetail(information, viewModelScope) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleFailure(failure: SandboxException) {
        super.handleFailure(failure)
        viewModelScope.launch {
            _uiState.value = UiState.Error(failure)
        }
    }

    private fun handleSuccess(success: GitHubItemDetails) {
        log("handleSuccess count : $success")
        viewModelScope.launch {
            _uiState.value = UiState.UpdateData(success)
        }
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemDetailViewModel", message, exception)
        }
    }
}
