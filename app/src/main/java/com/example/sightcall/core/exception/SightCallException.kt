package com.example.sightcall.core.exception

sealed class SightCallException: Exception() {
    class PreferenceKeyException(override val message: String) : SightCallException()
    object EmptyParamsException : SightCallException()
    object NetworkConnectionException : SightCallException()
    class ServerErrorException(override val message: String? = null) : SightCallException()
}
