package com.srj.productapp.data.source.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class AuthSettings(private val settings: Settings) {

    fun saveLoginState(isLoggedIn: Boolean) {
        settings[IS_LOGGED_IN_KEY] = isLoggedIn
    }

    fun isUserLoggedIn(): Boolean {
        return settings.getBoolean(IS_LOGGED_IN_KEY, false)
    }

    fun clearSettings() {
        settings.clear()
    }

    companion object {
        private const val IS_LOGGED_IN_KEY = "is_logged_in"
    }
}