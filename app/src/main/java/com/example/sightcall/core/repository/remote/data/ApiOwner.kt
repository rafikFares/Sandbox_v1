package com.example.sightcall.core.repository.remote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiOwner(
    @SerialName("avatar_url")
    val avatarUrl: String = "",
    @SerialName("gravatar_id")
    val gravatarId: String = "",
    @SerialName("html_url")
    val htmlUrl: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("login")
    val login: String = "",
    @SerialName("node_id")
    val nodeId: String = "",
    @SerialName("site_admin")
    val siteAdmin: Boolean = false,
    @SerialName("starred_url")
    val starredUrl: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("url")
    val url: String = ""
)
