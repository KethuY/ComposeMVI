package com.example.composearchsample.login

import androidx.lifecycle.viewModelScope
import com.example.composearchsample.base.BaseMVIViewModel
import com.example.composearchsample.data.datastore.DataStoreConstants
import com.example.composearchsample.data.datastore.DataStoreRepository
import com.example.composearchsample.data.repos.UserRepository
import com.example.composearchsample.network.Result
import com.example.composearchsample.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Kethu on 06/07/2024.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseMVIViewModel<LoginEvent>() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    override fun onEvent(screenEvent: LoginEvent) {
        when (screenEvent) {
           is LoginEvent.ClearError -> clearError()
           is LoginEvent.LoginClicked -> loginClicked()
            is LoginEvent.ValidateEmail -> validateEmail(screenEvent.email)
            is LoginEvent.ValidatePassword -> validatePassword(screenEvent.password)
        }
    }

    private  fun loginClicked() {
        userRepository.verifyUser(uiState.value.email.first,uiState.value.password.first)
            .onEach { res->
                _uiState.update {
                    it.copy(isLoading = Result.Loading == res)
                }
                when (res) {
                    is Result.Success -> {
                        dataStoreRepository.putPreference(
                            DataStoreConstants.IS_USER_SIGNED_IN,
                            true
                        )
                        _uiState.update {
                            it.copy(isLoggedInSuccessfully = true)
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(isError = true)
                        }
                    }
                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun clearError() {
        _uiState.update {
            it.copy(isError = false)
        }
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

    private fun isFormValid() {
        _uiState.update {
            val isValidForm = it.password.second && it.email.second
            it.copy(isValidForm = isValidForm)
        }
    }
}