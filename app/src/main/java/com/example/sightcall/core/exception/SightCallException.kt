package com.example.sightcall.core.exception

sealed class SightCallException: Exception() {
    class PreferenceKeyException(override val message: String) : SightCallException()
}
