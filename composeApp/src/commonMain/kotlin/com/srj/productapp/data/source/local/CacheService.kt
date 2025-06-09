package com.srj.productapp.data.source.local

import com.russhwolf.settings.Settings
import kotlinx.datetime.Clock

class CacheService(private val settings: Settings) {
    companion object {
        private const val PROFILE_CACHE_KEY = "user_profile_cache"
        private const val CACHE_TIMESTAMP_KEY = "cache_timestamp"
        private const val CACHE_TTL_HOURS = 24
    }

    fun saveProfile(profileJson: String) {
        settings.putString(PROFILE_CACHE_KEY, profileJson)
        settings.putLong(CACHE_TIMESTAMP_KEY, Clock.System.now().toEpochMilliseconds())
    }

    fun getProfile(): String? {
        return settings.getStringOrNull(PROFILE_CACHE_KEY)
    }

    fun isCacheExpired(): Boolean {
        val lastCacheTime = settings.getLongOrNull(CACHE_TIMESTAMP_KEY) ?: return true
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val diffHours = (currentTime - lastCacheTime) / (1000 * 60 * 60)
        return diffHours >= CACHE_TTL_HOURS
    }
}