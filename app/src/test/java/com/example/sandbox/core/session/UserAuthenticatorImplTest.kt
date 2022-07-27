package com.example.sandbox.core.session

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import com.example.sandbox.core.repository.preference.PreferenceRepository
import com.example.sandbox.core.utils.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf


class UserAuthenticatorImplTest : BaseUnitTest() {
    @MockK
    private lateinit var preferenceRepository: PreferenceRepository

    private lateinit var userAuthenticator: UserAuthenticator

    @BeforeTest
    fun setUp() {
        userAuthenticator = UserAuthenticatorImpl(preferenceRepository)
        every {
            runBlocking {
                preferenceRepository.save(any(), any() as String)
            }
        } returns Unit
    }

    @Test
    fun testLoginUserAdminSuccess() = runBlocking {
        val adminUser = UserAuthenticator.User.default
        val result = userAuthenticator.login(adminUser)

        result shouldBeInstanceOf Either.Success::class.java
    }

    @Test
    fun testLoginUserException(): Unit = runBlocking {
        val adminUser = UserAuthenticator.User("aaaaa", "bbbbb")
        val result = userAuthenticator.login(adminUser)

        result shouldBeInstanceOf Either.Failure::class.java
        val exception = (result as Either.Failure).value
        exception shouldBeEqualTo SandboxException.LoginException("$adminUser")
    }
}
