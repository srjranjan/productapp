package com.srj.productapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.srj.productapp.data.source.local.AuthSettings
import com.srj.productapp.navigation.HomeScreen
import com.srj.productapp.navigation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val authSettings: AuthSettings = koinInject<AuthSettings>()
        val initialScreen = if (authSettings.isUserLoggedIn()) {
            HomeScreen
        } else {
            LoginScreen
        }
        Navigator(initialScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}
