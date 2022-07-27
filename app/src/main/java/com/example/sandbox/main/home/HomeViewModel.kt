package com.example.sandbox.main.home

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sandbox.BuildConfig
import com.example.sandbox.core.repository.data.GitHubItem
import com.example.sandbox.core.usecase.FetchGitHubItems
import com.example.sandbox.main.detail.ItemDetailViewModel
import com.example.sandbox.main.home.adapter.HomeAdapter
import com.example.sandbox.main.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [HomeViewModel::class])
class HomeViewModel(
    private val fetchGitHubItems: FetchGitHubItems
) : BaseViewModel() {

    sealed interface UiState : BaseUiState {
        object Init : UiState
        data class ItemClick(val item: GitHubItem) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    override val uiState: StateFlow<BaseUiState> = _uiState

    private val _gitHubItems: MutableLiveData<List<GitHubItem>> = MutableLiveData()
    val gitHubItems: LiveData<List<GitHubItem>> = _gitHubItems

    val homeAdapter: HomeAdapter = HomeAdapter(this)

    override fun onCreate(owner: LifecycleOwner) {
        loadData() // default with "text" as string
    }

    private fun loadData(params: String? = null) = fetchGitHubItems(params, viewModelScope) {
        it.fold(::handleFailure, ::handleSuccess)
    }

    private fun handleSuccess(success: List<GitHubItem>) {
        log("handleSuccess count : ${success.count()}")
        _gitHubItems.value = success
        homeAdapter.submitList(success)
    }

    fun handleItemClick(item: GitHubItem) {
        log("handleItemClick item : $item")
        viewModelScope.launch {
            _uiState.value = UiState.ItemClick(item)
        }
    }

    val searchAction: (String) -> Unit = {
        log("searchAction content : $it")
        loadData(it)
    }

    override fun log(message: String, exception: Exception?) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemsViewModel", message, exception)
        }
    }
}
