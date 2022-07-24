package com.example.sightcall.core.repository.remote.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiLicense(
    @SerialName("spdx_id")
    val spdxId: String = ""
)
