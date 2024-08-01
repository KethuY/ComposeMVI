package com.example.composearchsample

sealed class MainEvent {
    data object GetUserStatus : MainEvent()
    data object ClearLoginPreferences : MainEvent()
}

data class MainUiState(val isNewUser: Boolean = false, val isSignInUser: Boolean = false)