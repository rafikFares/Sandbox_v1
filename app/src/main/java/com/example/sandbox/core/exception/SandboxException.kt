package com.example.sandbox.core.exception

sealed class SandboxException: Exception() {
    class PreferenceKeyException(override val message: String) : SandboxException()
    object EmptyParamsException : SandboxException()
    object NetworkConnectionException : SandboxException()
    class ServerErrorException(override val message: String? = null) : SandboxException()
    class DatabaseErrorException(override val message: String? = null) : SandboxException()
    class ElementNotFoundException(val elementName:String, override val message: String? = null) : SandboxException()
}
