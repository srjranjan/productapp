package com.srj.productapp.presentation.ui.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.srj.productapp.data.EmailValidator
import com.srj.productapp.data.source.local.AuthSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Represents the state of the Login screen
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoginSuccess: Boolean = false,
)

class LoginViewModel(private val authSettings: AuthSettings) : ScreenModel {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    fun onEmailChange(email: String) {
        val emailError = if (isValidEmail(email)) null else "Invalid email format"
        _uiState.update { it.copy(email = email, emailError = emailError) }

    }

    fun onPasswordChange(password: String) {
        val passwordError = if (password.length >= 8) null else "Password must be at least 8 characters"
        _uiState.update { it.copy(password = password, passwordError = passwordError) }

    }

    fun login() {
        if (!isFormValid()) return

        screenModelScope.launch {
            // Here you would typically call a repository to authenticate with a server.
            // For this local example, we'll just save the login state directly.
            authSettings.saveLoginState(true)
            _uiState.update { it.copy(isLoginSuccess = true) }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return EmailValidator.isValid(email)
    }

    private fun isFormValid(): Boolean {
        val state = _uiState.value
        val isEmailValid = isValidEmail(state.email)
        val isPasswordValid = state.password.length >= 8
        onEmailChange(state.email)
        onPasswordChange(state.password)
        return isEmailValid && isPasswordValid
    }
}