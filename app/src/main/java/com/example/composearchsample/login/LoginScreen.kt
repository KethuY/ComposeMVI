package com.example.composearchsample.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.composearchsample.R
import com.example.composearchsample.components.ShowDialog
import com.example.composearchsample.ui.theme.PurpleGrey80

@Composable
fun LoginScreen(
    onEvent: (LoginEvent) -> Unit,
    state: State<LoginUiState>,
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PurpleGrey80, shape = RectangleShape),
        contentAlignment = Alignment.Center

    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.cardColors()

        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.login_to_application),
                    modifier = commonModifier,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = state.value.email.first,
                    onValueChange = {
                        onEvent.invoke(LoginEvent.ValidateEmail(it))
                    },
                    label = { Text(stringResource(R.string.email)) },
                    modifier = commonModifier,
                    colors = colors,
                    isError = state.value.email.second.not(),
                )

                OutlinedTextField(
                    value = state.value.password.first,
                    onValueChange = {
                        onEvent.invoke(LoginEvent.ValidatePassword(it))
                    },
                    label = { Text(stringResource(R.string.password)) },
                    modifier = commonModifier,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = colors,
                    isError = state.value.password.second.not(),
                )

                Button(
                    onClick = { onEvent.invoke(LoginEvent.LoginClicked) },
                    enabled = state.value.isValidForm
                ) {
                    Text(text = stringResource(R.string.login))
                }

                BackHandler {
                    onAppExist()
                }
            }
        }

        when {
            state.value.isLoading -> CircularProgressIndicator(color = Color.Blue)
            state.value.isLoggedInSuccessfully -> onNavigateToLanding()
            state.value.isError -> ShowDialog(
                title = stringResource(R.string.error),
                message = stringResource(R.string.something_went_wrong),
                onConfirmation = { onEvent.invoke(LoginEvent.ClearError) })
        }
    }

}