package com.example.sightcall.main.platform

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sightcall.core.exception.SightCallException
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    interface BaseUiState

    abstract val uiState: StateFlow<BaseUiState?>

    private val _failure: MutableLiveData<SightCallException> = MutableLiveData()
    val failure: LiveData<SightCallException> = _failure

    protected abstract fun log(message: String, exception: Exception? = null)

    protected open fun handleFailure(failure: SightCallException) {
        log("handleFailure", failure)
        _failure.value = failure
    }
}
