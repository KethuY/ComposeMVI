package com.example.composearchsample.login

/**
 * Created by Kethu on 06/07/2024.
 */
sealed class LoginEvent {
    data class ValidateEmail(val email: String) : LoginEvent()
    data class ValidatePassword(val password: String) : LoginEvent()
    data object LoginClicked : LoginEvent()
    data object ClearError : LoginEvent()
}