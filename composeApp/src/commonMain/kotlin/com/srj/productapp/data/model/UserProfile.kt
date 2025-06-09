package com.srj.productapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String,
    val email: String,
    val memberSince: String,
    val bio: String,
    val imageUrl: String,
    val location: String?,
)