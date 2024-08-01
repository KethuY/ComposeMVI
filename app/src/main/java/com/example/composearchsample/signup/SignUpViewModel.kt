package com.example.composearchsample.signup

import androidx.lifecycle.viewModelScope
import com.example.composearchsample.base.BaseMVIViewModel
import com.example.composearchsample.data.datastore.DataStoreConstants.IS_NEW_USER
import com.example.composearchsample.data.datastore.DataStoreRepository
import com.example.composearchsample.data.repos.UserRepository
import com.example.composearchsample.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kethu on 20/06/2024.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseMVIViewModel<SignUpEvent>() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    override fun onEvent(screenEvent: SignUpEvent) {
        when (screenEvent) {
            is SignUpEvent.ValidateName -> validateUserName(screenEvent.userName)
            is SignUpEvent.SignUpClick -> saveUser()
            is SignUpEvent.ValidateConfirmPassword -> validateConfirmPassword(screenEvent.confirmPassword)
            is SignUpEvent.ValidateEmail -> validateEmail(screenEvent.email)
            is SignUpEvent.ValidatePassword -> validatePassword(screenEvent.password)
            is SignUpEvent.ClearError -> clearError()
        }
    }

    private fun clearError() {
        _uiState.update {
            it.copy(isError = false)
        }
    }

    private fun saveUser() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        val userName = _uiState.value.userName.first
        val email = _uiState.value.email.first
        val password = _uiState.value.password.first
        viewModelScope.launch {
            userRepository.addUser(userName = userName, password = password, email = email)
            dataStoreRepository.putPreference(IS_NEW_USER, false)
        }
        _uiState.update {
            it.copy(isUserSignedUp = true, isLoading = false)
        }
    }

    private fun validateUserName(name: String) {
        val isNotValidUser =
            name.isNotBlank() && (name.trimStart() == "0" || name.length < 3 || name.length > 30)
        _uiState.update {
            it.copy(userName = name to isNotValidUser.not())
        }
        isFormValid()
    }

    private fun validateEmail(email: String) {
        _uiState.update {
            it.copy(email = email to (email.isNotBlank() && Utils.isValidEmail(email)))
        }
        isFormValid()
    }

    private fun validatePassword(password: String) {
        val isValidPwd = (password.isNotBlank() && password.length < 3).not()
        _uiState.update {
            it.copy(password = password to isValidPwd)
        }
        isFormValid()
    }

    private fun validateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            val isValidConfirmPassword =
                confirmPassword.isNotBlank() && it.password.first == confirmPassword
            it.copy(confirmPassword = confirmPassword to isValidConfirmPassword)
        }
        isFormValid()
    }

    private fun isFormValid() {
        _uiState.update {
            val isValidForm =
                it.password.second && it.confirmPassword.second && it.email.second && it.userName.second
            it.copy(isFormValid = isValidForm)
        }
    }
}