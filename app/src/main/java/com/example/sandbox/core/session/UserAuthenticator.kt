package com.example.sandbox.core.session


interface UserAuthenticator {

    data class User(
        val userName: String,
        val password: String
    ) {
        companion object {
            val default = User(
                userName = "admin",
                password = "admin"
            )
        }
    }

    fun login(user: User): Boolean
    fun logOut(): Boolean
}
