package com.srj.productapp.presentation.ui.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.srj.productapp.data.model.UserProfile
import com.srj.productapp.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val profile: UserProfile, val isRefreshing: Boolean = false) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}


class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
) : ScreenModel {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfile()
    }

    fun fetchProfile(isManualRefresh: Boolean = false) {

        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isRefreshing = true)
        } else {
            _uiState.value = ProfileUiState.Loading
        }
        screenModelScope.launch {
            if (isManualRefresh) {
                delay(1000)
            }
            _uiState.value = ProfileUiState.Loading
            try {
                val profile = getProfileUseCase()
                _uiState.value = ProfileUiState.Success(profile = profile, isRefreshing = false)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }

        }
    }
}