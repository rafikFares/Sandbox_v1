package com.example.sightcall.core.session

import org.koin.core.annotation.Single

@Single
class UserSessionHandler: UserSession {
    override fun isUserLoggedIn(): Boolean = true
}
