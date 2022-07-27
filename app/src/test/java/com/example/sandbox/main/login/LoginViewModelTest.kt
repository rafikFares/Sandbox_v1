package com.example.sandbox.main.login

import com.example.sandbox.BaseAndroidTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.session.UserAuthenticator
import com.example.sandbox.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe


class LoginViewModelTest : BaseAndroidTest() {

    @MockK
    private lateinit var userAuthenticator: UserAuthenticator

    private lateinit var loginViewModel: LoginViewModel

    @BeforeTest
    fun setUp() {
        loginViewModel = LoginViewModel(userAuthenticator)
    }

    @Test
    fun testLoginSuccessUpdateUiStateLiveData() {
        every {
            runBlocking {
                userAuthenticator.login(any())
            }
        } returns Either.Success(true)

        loginViewModel.login("test", "test")
        val successResult = loginViewModel.uiState.value

        successResult shouldBe LoginViewModel.UiState.LoginSuccess
    }

    @Test
    fun testLoginFailUpdateUiStateLiveData() {
        val exception = SandboxException.LoginException("nothing")
        every {
            runBlocking {
                userAuthenticator.login(any())
            }
        } returns Either.Failure(exception)

        loginViewModel.login("test", "test")
        val successResult = loginViewModel.uiState.value
        val errorResult = loginViewModel.failure.value

        successResult shouldBe LoginViewModel.UiState.LoginError
        errorResult shouldBe exception
    }
}