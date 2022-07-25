package com.example.sightcall.main.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sightcall.BuildConfig
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.repository.data.GitHubItemDetails
import com.example.sightcall.core.repository.remote.RemoteRepository
import com.example.sightcall.core.usecase.FetchGitHubItemDetails
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class GitHubItemDetailViewModel(
    private val fetchGitHubItemDetails: FetchGitHubItemDetails
) : ViewModel() {

    private val _failure: MutableLiveData<SightCallException> = MutableLiveData()
    val failure: LiveData<SightCallException> = _failure

    private val _gitHubItemDetails: MutableLiveData<GitHubItemDetails> = MutableLiveData()
    val gitHubItemDetails: LiveData<GitHubItemDetails> = _gitHubItemDetails

    fun loadData(information: RemoteRepository.RepositoryInformation) = fetchGitHubItemDetails(information, viewModelScope) {
        it.fold(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: SightCallException) {
        log("handleFailure", failure)
        _failure.value = failure
    }

    private fun handleSuccess(success: GitHubItemDetails) {
        log("handleSuccess count : $success")
        _gitHubItemDetails.value = success
    }


    private fun log(message: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemDetailViewModel", message, exception)
        }
    }
}
