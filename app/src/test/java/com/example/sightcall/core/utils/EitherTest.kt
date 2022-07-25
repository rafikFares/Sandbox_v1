package com.example.sightcall.core.utils

import com.example.sightcall.BaseUnitTest
import com.example.sightcall.core.exception.SightCallException
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
        val eitherFailure = Either.Failure(SightCallException.EmptyParamsException)

        eitherFailure shouldBeInstanceOf Either::class.java
        eitherFailure.isSuccessful shouldBe false
        eitherFailure.fold(
            {
                it shouldBeInstanceOf SightCallException::class.java
                it shouldBeEqualTo SightCallException.EmptyParamsException
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
