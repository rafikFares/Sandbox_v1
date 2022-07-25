package com.example.sightcall.main.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sightcall.BuildConfig
import com.example.sightcall.core.session.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class StartViewModel(private val userSession: UserSession) : ViewModel() {

    enum class UiState {
        Init, Connected, NotConnected
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            log("checking if user is logged in")
            delay(500) // just to mock the request timing
            if (userSession.isUserLoggedIn()) {
                _uiState.value = UiState.Connected
            } else {
                _uiState.value = UiState.NotConnected
            }
        }
    }

    private fun log(message: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.d("StartViewModel", message, exception)
        }
    }
}
