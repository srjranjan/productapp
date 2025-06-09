package com.srj.productapp.network.repository

import com.russhwolf.settings.Settings
import com.srj.productapp.data.model.UserProfile
import com.srj.productapp.network.MainRepository
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

class ProfileRepository(private val mainRepository: MainRepository, private val profileCache: ProfileCache) {
    private var cachedProfile: Pair<Long, UserProfile>? = null

    suspend fun getProfile(): UserProfile {
        val cached = profileCache.getCachedProfile()
        return if (cached != null) {
            Json.decodeFromString(cached)
        } else {
            val profile = mainRepository.getProfile()
            profileCache.saveProfile(Json.encodeToString(profile))
            profile
        }
    }
}

class ProfileCache(private val settings: Settings) {
    companion object {
        private const val PROFILE_KEY = "user_profile_json"
        private const val PROFILE_TIMESTAMP = "user_profile_timestamp"
    }

    fun saveProfile(json: String) {
        settings.putString(PROFILE_KEY, json)
        settings.putLong(PROFILE_TIMESTAMP, Clock.System.now().toEpochMilliseconds())
    }

    fun getCachedProfile(): String? {
        val time = settings.getLongOrNull(PROFILE_TIMESTAMP) ?: return null
        val now = Clock.System.now().toEpochMilliseconds()
        return if (now - time < 24 * 60 * 60 * 1000) settings.getStringOrNull(PROFILE_KEY) else null
    }
}