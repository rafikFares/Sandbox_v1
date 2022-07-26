package com.example.sandbox.core.repository.remote.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGitHubLicense(
    @SerialName("spdx_id")
    val spdxId: String = ""
)
