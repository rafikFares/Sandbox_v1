package com.example.sightcall.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sightcall.BuildConfig
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItem
import com.example.sightcall.core.usecase.FetchGitHubItems
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class GitHubItemsViewModel(
    private val fetchGitHubItems: FetchGitHubItems
) : ViewModel() {

    private val _failure: MutableLiveData<SightCallException> = MutableLiveData()
    val failure: LiveData<SightCallException> = _failure

    private val _gitHubItems: MutableLiveData<List<GitHubItem>> = MutableLiveData()
    val gitHubItems: LiveData<List<GitHubItem>> = _gitHubItems

    fun loadData() = fetchGitHubItems(null, viewModelScope) {
        it.fold(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: SightCallException) {
        log("handleFailure", failure)
        _failure.value = failure
    }

    private fun handleSuccess(success: List<GitHubItem>) {
        log("handleSuccess count : ${success.count()}")
        _gitHubItems.value = success
    }


    private fun log(message: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemsViewModel", message, exception)
        }
    }
}
