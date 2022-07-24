package com.example.sightcall.core.utils


sealed interface Either<out F, out S> {
    data class Success<out S>(val value: S) : Either<Nothing, S>
    data class Failure<out F>(val value: F) : Either<F, Nothing>

    val isSuccessful
        get() = this is Success

    fun ifIsSuccessThan(function: (data: S) -> Unit): Either<F, S> {
        (this as? Success)?.let {
            function.invoke(it.value)
        }
        return this
    }

    /**
     * Applies failureTask if this is a Failure or successTask if this is a Success.
     */
    fun fold(failureTask: (F) -> Any, successTask: (S) -> Any): Any =
        when (this) {
            is Failure -> failureTask(value)
            is Success -> successTask(value)
        }
}
