package com.example.sandbox.core.session

import org.koin.core.annotation.Single

@Single
class UserUserAuthenticatorImpl : UserAuthenticator {
    override fun login(user: UserAuthenticator.User): Boolean {
        TODO("Not yet implemented")
    }

    override fun logOut(): Boolean {
        TODO("Not yet implemented")
    }
}
