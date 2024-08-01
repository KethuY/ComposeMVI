package com.example.composearchsample.signup

data class SignUpUiState(
    val userName: Pair<String, Boolean> = Pair("", false),
    val email: Pair<String, Boolean> = Pair("", false),
    val password: Pair<String, Boolean> = Pair("", false),
    val confirmPassword: Pair<String, Boolean> = Pair("", false),
    val isFormValid: Boolean = false,
    val isUserSignedUp: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
