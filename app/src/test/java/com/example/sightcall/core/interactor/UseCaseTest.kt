package com.example.sightcall.core.interactor

import com.example.sightcall.BaseUnitTest
import com.example.sightcall.core.exception.SightCallException
import com.example.sightcall.core.utils.Either
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf

class UseCaseTest : BaseUnitTest() {

    private class TestClass : UseCase<String, String>() {
        override suspend fun run(params: String?): Either<SightCallException, String> {
            return Either.Success("$params + run")
        }
    }

    @Test
    fun runReturnSuccess() {
        val testClass = TestClass()
        val result = runBlocking {
            testClass.run("params")
        }

        result shouldBeInstanceOf Either.Success::class.java
        result shouldBeEqualTo Either.Success("params + run")
    }
}
