package com.srj.productapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val description: String,
    val images: List<String>,
)


