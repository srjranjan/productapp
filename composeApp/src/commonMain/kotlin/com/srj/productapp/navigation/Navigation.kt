package com.srj.productapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.srj.productapp.data.model.Product
import com.srj.productapp.data.source.local.AuthSettings
import com.srj.productapp.presentation.ui.home.HomeScreenContent
import com.srj.productapp.presentation.ui.home.HomeViewModel
import com.srj.productapp.presentation.ui.login.LoginScreenContent
import com.srj.productapp.presentation.ui.login.LoginViewModel
import com.srj.productapp.presentation.ui.product.ImageGalleryScreen
import com.srj.productapp.presentation.ui.product.ProductDetailContent
import com.srj.productapp.presentation.ui.profile.ProfileScreenContent
import com.srj.productapp.presentation.ui.profile.ProfileViewModel
import org.koin.compose.koinInject


object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<LoginViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(uiState.isLoginSuccess) {
            if (uiState.isLoginSuccess) {
                navigator.replaceAll(HomeScreen)
            }
        }
        LoginScreenContent(
            uiState = uiState,
            onLoginClick = viewModel::login,
            onEmailChange = { viewModel.onEmailChange(email = it) },
            onPasswordChange = { viewModel.onPasswordChange(password = it) }
        )
    }
}

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        HomeScreenContent(
            uiState = uiState,
            onProductClick = { product ->
                navigator.push(item = DetailScreen(product = product))
            },
            onProfileClick = {
                navigator.push(item = ProfileScreen)
            }
        )
    }

}

data class DetailScreen(val product: Product) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProductDetailContent(
            product = product,
            onImageClick = { images ->
                navigator.push(item = FullScreenImage(imageUrl = images))
            },
            onBackClick = { navigator.pop() }
        )
    }
}

object ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val authSettings: AuthSettings = koinInject<AuthSettings>()

        ProfileScreenContent(
            uiState = uiState,
            onBackClick = { navigator.pop() },
            onRefresh = { viewModel.fetchProfile(isManualRefresh = true) },
            onProfileImageClick = { imageUrl ->
                navigator.push(
                    item = FullScreenImage(
                        imageUrl = listOf(element = imageUrl),
                        isProfileImage = true
                    )
                )
            },
            onConfirmLogout = {
                authSettings.clearSettings()
                navigator.replaceAll(LoginScreen)
            }
        )
    }
}

data class FullScreenImage(val imageUrl: List<String>, val isProfileImage: Boolean = false) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ImageGalleryScreen(
            imageUrls = imageUrl,
            isProfileImage = isProfileImage,
            onNavigateBack = { navigator.pop() }
        )
    }
}
