package com.example.sightcall.core.repository.remote

import com.example.sightcall.core.api.Api
import org.koin.core.annotation.Single

@Single
class RemoteDataRepository(
    private val api: Api
): RemoteRepository {

}
