package com.example.sandbox.main.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityLoginBinding
import com.example.sandbox.main.home.HomeActivity
import com.example.sandbox.main.ui.login.Loader
import com.example.sandbox.main.ui.material.SandboxTheme
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.clickWithDebounce
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : ComponentActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()
    //private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SandboxTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Loader()
                }
            }
        }

        initActions()
    }

    private fun initActions() {
//        with(binding) {
//            loginButton.clickWithDebounce {
//                val userName = loginPasswordView.userNameText
//                val password = loginPasswordView.passwordText
//                loginViewModel.login(userName, password)
//            }
//        }
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                loginViewModel.uiState
//                    .collect { state ->
//                        when (state) {
//                            LoginViewModel.UiState.LoginSuccess -> {
//                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                                finish()
//                            }
//                            LoginViewModel.UiState.LoginError -> {
//                                binding.loginPasswordView.showErrorState(StringSource.Res(R.string.login_error_message))
//                            }
//                            else -> {
//                                // do nothing
//                            }
//                        }
//                    }
//            }
//        }
    }

}
