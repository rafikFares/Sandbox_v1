package com.example.sandbox.core.utils

import com.example.sandbox.BaseUnitTest
import com.example.sandbox.core.exception.SandboxException
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import kotlin.test.Test

class EitherTest : BaseUnitTest() {

    @Test
    fun successIsCorrect() {
        val eitherSuccess = Either.Success("tmp")

        eitherSuccess shouldBeInstanceOf Either::class.java
        eitherSuccess.isSuccessful shouldBe true
        eitherSuccess.fold(
            {},
            {
                it shouldBeInstanceOf String::class.java
                it shouldBeEqualTo "tmp"
            }
        )
    }

    @Test
    fun failureIsCorrect() {
        val eitherFailure = Either.Failure(SandboxException.EmptyParamsException)

        eitherFailure shouldBeInstanceOf Either::class.java
        eitherFailure.isSuccessful shouldBe false
        eitherFailure.fold(
            {
                it shouldBeInstanceOf SandboxException::class.java
                it shouldBeEqualTo SandboxException.EmptyParamsException
            },
            {}
        )
    }


    @Test
    fun foldRunSuccessTask() {
        val eitherSuccess = Either.Success("tmp")
        val result = eitherSuccess.fold(
            {
                fail("Error")
            },
            {
                it
            }
        )

        result shouldBeEqualTo "tmp"
    }
}
