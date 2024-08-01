package com.example.composearchsample.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.composearchsample.components.CustomDialog

@Composable
fun SignUpScreen(
    onEvent: (SignUpEvent) -> Unit,
    state: State<SignUpUiState>,
    onAppExist: () -> Unit,
    onNavigateToLanding: () -> Unit
) {
    val commonModifier = Modifier.padding(bottom = 16.dp)
    val colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Blue,
        focusedTextColor = Color.Blue,
        focusedLabelColor = Color.Blue,
        cursorColor = Color.Blue,
        errorBorderColor = Color.Red,
        errorTextColor = Color.Red,
        errorLabelColor = Color.Red,
        errorCursorColor = Color.Red,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to adib bank",
            modifier = commonModifier,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = state.value.userName.first,
            onValueChange = {
                onEvent.invoke(SignUpEvent.ValidateName(it))
            },
            label = { Text("User name") },
            modifier = commonModifier,
            isError = state.value.userName.second.not(),
            colors = colors,
            supportingText = {
                if (state.value.userName.second) {
                    Text(
                        text = "Please enter valid name",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        )
        OutlinedTextField(
            value = state.value.email.first,
            onValueChange = {
                onEvent.invoke(SignUpEvent.ValidateEmail(it))
            },
            label = { Text("Email") },
            modifier = commonModifier,
            colors = colors,
            isError = state.value.email.second.not(),
        )

        OutlinedTextField(
            value = state.value.password.first,
            onValueChange = {
                onEvent.invoke(SignUpEvent.ValidatePassword(it))
            },
            label = { Text("Password") },
            modifier = commonModifier,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = colors,
            isError = state.value.password.second.not(),
        )
        OutlinedTextField(
            value = state.value.confirmPassword.first,
            onValueChange = { onEvent.invoke(SignUpEvent.ValidateConfirmPassword(it)) },
            label = { Text("Confirm password") },
            modifier = commonModifier,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = colors,
            isError = state.value.confirmPassword.second.not(),
        )

        Button(
            onClick = { onEvent.invoke(SignUpEvent.SignUpClick) },
            enabled = state.value.isFormValid
        ) {
            Text(text = "Sign Up")
        }

        TextButton(onClick = { /*TODO*/ }, commonModifier.align(Alignment.End)) {
            Text(text = "Already a user?Login")
        }

        BackHandler {
            onAppExist()
            // handle back event
        }
    }
    when {
        state.value.isLoading -> CircularProgressIndicator(color = Color.Blue)
        state.value.isUserSignedUp -> onNavigateToLanding()
        state.value.isError -> CustomDialog(title="Error", message = "Something went wrong", onConfirmation = {onEvent.invoke(SignUpEvent.ClearError)})
    }
}