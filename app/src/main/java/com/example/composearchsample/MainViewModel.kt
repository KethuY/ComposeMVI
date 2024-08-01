package com.example.composearchsample

import androidx.lifecycle.viewModelScope
import com.example.composearchsample.base.BaseMVIViewModel
import com.example.composearchsample.data.datastore.DataStoreConstants
import com.example.composearchsample.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kethu on 27/06/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val dateStoreRepository: DataStoreRepository) :
    BaseMVIViewModel<MainEvent>() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    override fun onEvent(screenEvent: MainEvent) {
        if (screenEvent is MainEvent.GetUserStatus) {
            getUserStatus()
        } else {
            clearLoginPrefs()
        }
    }

    private fun clearLoginPrefs() {
        viewModelScope.launch {
            dateStoreRepository.putPreference(DataStoreConstants.IS_USER_SIGNED_IN, false)
        }
    }

    private fun getUserStatus() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isNewUser = dateStoreRepository.getPreference(
                        DataStoreConstants.IS_NEW_USER,
                        true
                    ),
                    isSignInUser = dateStoreRepository.getPreference(
                        DataStoreConstants.IS_USER_SIGNED_IN,
                        false
                    )
                )
            }
        }
    }
}