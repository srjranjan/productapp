package com.srj.productapp.domain.repository

import com.srj.productapp.data.model.UserProfile

interface IProfileRepository {
    suspend fun getProfile(): UserProfile
}