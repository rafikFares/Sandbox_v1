package com.example.sightcall.main.home

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sightcall.BuildConfig
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.usecase.FetchGitHubItems
import com.example.sightcall.main.home.adapter.GitHubItemsAdapter
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class GitHubItemsViewModel(
    private val fetchGitHubItems: FetchGitHubItems
) : ViewModel(), DefaultLifecycleObserver {

    private val _failure: MutableLiveData<SightCallException> = MutableLiveData()
    val failure: LiveData<SightCallException> = _failure

    private val _gitHubItems: MutableLiveData<List<GitHubItem>> = MutableLiveData()
    val gitHubItems: LiveData<List<GitHubItem>> = _gitHubItems

    val gitHubItemsAdapter: GitHubItemsAdapter = GitHubItemsAdapter(this)

    override fun onCreate(owner: LifecycleOwner) {
        loadData() // default with "text" as string
    }

    private fun loadData(params: String? = null) = fetchGitHubItems(params, viewModelScope) {
        it.fold(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: SightCallException) {
        log("handleFailure", failure)
        _failure.value = failure
    }

    private fun handleSuccess(success: List<GitHubItem>) {
        log("handleSuccess count : ${success.count()}")
        _gitHubItems.value = success
        gitHubItemsAdapter.submitList(success)
    }

    fun handleItemClick(item: GitHubItem) {
        log("handleItemClick item : $item")
    }

    val searchAction: (String) -> Unit = {
        log("searchAction content : $it")
        loadData(it)
    }

    private fun log(message: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemsViewModel", message, exception)
        }
    }
}
