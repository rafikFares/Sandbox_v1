package com.example.sightcall.core.session

import org.koin.core.annotation.Single

@Single
class UserAuthenticator : Authenticator {
    override fun login(user: Authenticator.User): Boolean {
        TODO("Not yet implemented")
    }

    override fun logOut(): Boolean {
        TODO("Not yet implemented")
    }
}
