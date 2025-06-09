package com.srj.productapp.data.repository

import com.srj.productapp.data.model.UserProfile
import com.srj.productapp.data.source.local.CacheService
import com.srj.productapp.data.source.remote.ApiService
import com.srj.productapp.domain.repository.IProfileRepository
import kotlinx.serialization.json.Json

class ProfileRepository(
    private val apiService: ApiService,
    private val cacheService: CacheService,
    private val json: Json,
) : IProfileRepository {
    override suspend fun getProfile(): UserProfile {
        if (!cacheService.isCacheExpired()) {
            cacheService.getProfile()?.let { cachedJson ->
                return json.decodeFromString<UserProfile>(cachedJson).toDomainModel()
            }
        }

        val profileFromApi = apiService.getUserProfile()

        val profileJson = json.encodeToString(profileFromApi)

        cacheService.saveProfile(profileJson)

        return profileFromApi.toDomainModel()
    }
}