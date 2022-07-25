package com.example.sightcall.main.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.sightcall.BuildConfig
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItemDetails
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.usecase.FetchGitHubItemDetails
import com.example.sightcall.main.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class GitHubItemDetailViewModel(
    private val fetchGitHubItemDetails: FetchGitHubItemDetails
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        object Loading : UiState
        data class UpdateData(val gitHubItemDetails: GitHubItemDetails) : UiState
        data class Error(val exception: SightCallException) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<UiState> = _uiState

    fun loadData(information: RemoteRepository.RepositoryInformation) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
        }
        fetchGitHubItemDetails(information, viewModelScope) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleFailure(failure: SightCallException) {
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
