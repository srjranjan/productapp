package com.srj.productapp.data.repository

import com.srj.productapp.data.model.Product
import com.srj.productapp.data.model.UserProfile

fun Product.toDomainModel() = Product(
    id = this.id,
    title = this.title,
    subtitle = this.subtitle,
    imageUrl = this.imageUrl,
    description = this.description,
    images = this.images
)

fun UserProfile.toDomainModel() = UserProfile(
    name = this.name,
    email = this.email,
    memberSince = this.memberSince,
    imageUrl = this.imageUrl,
    bio = this.bio,
    location = this.location
)

