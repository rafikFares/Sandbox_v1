package com.example.sightcall.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sightcall.login.LoginActivity
import com.example.sightcall.main.GitHubItemsActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class StartActivity : AppCompatActivity() {

    private val startViewModel by viewModel<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        initActions()
    }

    private fun initActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                startViewModel.uiState
                    .collect { state ->
                        when (state) {
                            StartViewModel.UiState.Connected -> {
                                startActivity(Intent(this@StartActivity, GitHubItemsActivity::class.java))
                                finish()
                            }
                            StartViewModel.UiState.NotConnected -> {
                                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
                                finish()
                            }
                            else -> {
                                // do nothing
                            }
                        }
                    }
            }
        }
    }
}
