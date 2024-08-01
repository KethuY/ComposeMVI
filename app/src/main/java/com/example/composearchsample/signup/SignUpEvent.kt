package com.example.composearchsample.signup

sealed class SignUpEvent {
    data class ValidateName(val userName: String) : SignUpEvent()
    data class ValidateEmail(val email: String) : SignUpEvent()
    data class ValidatePassword(val password: String) : SignUpEvent()
    data class ValidateConfirmPassword(val confirmPassword: String) : SignUpEvent()
    data object SignUpClick : SignUpEvent()
    data object ClearError : SignUpEvent()
}
