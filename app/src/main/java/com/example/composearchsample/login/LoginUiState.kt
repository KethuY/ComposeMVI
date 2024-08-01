package com.example.composearchsample.login

data class LoginUiState(
    val email: Pair<String, Boolean> = Pair("", false),
    val password: Pair<String, Boolean> = Pair("", false),
    val isLoggedInSuccessfully: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false
)
