package com.example.sandbox.core.session

import org.koin.core.annotation.Single

@Single
class UserSessionImpl: UserSession {
    override fun isUserLoggedIn(): Boolean = true
}
