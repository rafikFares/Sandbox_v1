package com.example.sightcall.core.repository.remote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiItem(
    val name: String = "",
    val owner: ApiOwner,
    val language: String = "",
    @SerialName("stargazers_count")
    val stars: Int = 0
)
