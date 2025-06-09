package com.srj.productapp.presentation.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onLoginClick: () -> Unit,
    onEmailChange: (email: String) -> Unit,
    onPasswordChange: (password: String) -> Unit,
) {
    val isNotEmpty = uiState.email.isNotBlank() && uiState.password.isNotBlank()
    val isButtonEnabled =
        mutableStateOf(isNotEmpty && uiState.emailError == null && uiState.passwordError == null)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        OutlinedTextField(
            value = uiState.email,
            keyboardActions = KeyboardActions {
                this.defaultKeyboardAction(ImeAction.Next)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                showKeyboardOnFocus = true
            ),
            singleLine = true,
            onValueChange = { onEmailChange(it) },
            label = { Text("Username") },
            isError = uiState.emailError != null,
            supportingText = { uiState.emailError?.let { Text(it) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onPasswordChange(it) },
            keyboardActions = KeyboardActions {
                this.defaultKeyboardAction(ImeAction.Next)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Go,
                showKeyboardOnFocus = true
            ),
            singleLine = true,
            label = { Text("Password") },
            isError = uiState.passwordError != null,
            supportingText = { uiState.passwordError?.let { Text(it) } },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)

        )

        Button(
            enabled = isButtonEnabled.value,
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Login", fontSize = 16.sp)
        }
    }
}