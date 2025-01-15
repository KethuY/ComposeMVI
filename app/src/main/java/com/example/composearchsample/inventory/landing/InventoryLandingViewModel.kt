package com.example.composearchsample.inventory.landing

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.composearchsample.base.BaseMVIViewModel
import com.example.composearchsample.data.repos.InventoryRepository
import com.example.composearchsample.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Kethu on 30/07/2024.
 */
@HiltViewModel
class InventoryLandingViewModel @Inject constructor(private val repository: InventoryRepository) :
    BaseMVIViewModel<InventoryLandingEvent>() {
    private val _uiState = MutableStateFlow(InventoryLandingUiState())
    val uiState: StateFlow<InventoryLandingUiState> = _uiState.asStateFlow()

    override fun onEvent(screenEvent: InventoryLandingEvent) {
        if (screenEvent == InventoryLandingEvent.GetInventories) {
            _uiState.update {
                it.copy(isApiCalled = true)
            }
            repository.getInventories().map { res ->
                Log.d("Kethu", "onEvent: getInventories called")
                _uiState.update {
                    it.copy(isLoading = Result.Loading == res, isApiCalled = true)
                }
                when (res) {
                    is Result.Success -> {
                        Log.d("Kethu", "onEvent:  success ${res.data}")
                        _uiState.update {
                            it.copy(inventories = res.data)
                        }
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
        }
    }
}