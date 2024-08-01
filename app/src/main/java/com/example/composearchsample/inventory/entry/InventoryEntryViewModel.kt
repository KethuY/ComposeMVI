package com.example.composearchsample.inventory.entry

import androidx.lifecycle.viewModelScope
import com.example.composearchsample.base.BaseMVIViewModel
import com.example.composearchsample.data.datastore.DataStoreConstants
import com.example.composearchsample.data.local.inventory.Inventory
import com.example.composearchsample.data.repos.InventoryRepository
import com.example.composearchsample.data.uimodels.InventoryDetails
import com.example.composearchsample.network.Result
import com.example.composearchsample.signup.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kethu on 20/06/2024.
 */
@HiltViewModel
class InventoryEntryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
) : BaseMVIViewModel<InventoryEntryEvent>() {

    private val _uiState = MutableStateFlow(InventoryEntryUiState())
    val uiState: StateFlow<InventoryEntryUiState> = _uiState.asStateFlow()
    override fun onEvent(screenEvent: InventoryEntryEvent) {
        when (screenEvent) {
            is InventoryEntryEvent.AddInventory -> saveInventoryDetails()
            is InventoryEntryEvent.UpdateUiState -> updateState(screenEvent.inventoryDetails)
        }
    }

    private fun saveInventoryDetails() {
        val data = uiState.value.inventoryDetails
        val inventData = Inventory(
            id = 0,
            name = data.name,
            price = data.price.toDoubleOrNull() ?: 0.0,
            quantity = data.quantity.toIntOrNull() ?: 0
        )

        inventoryRepository.addInventory(inventData).map { res ->
            _uiState.update {
                it.copy(isLoading = Result.Loading == res)
            }
            when (res) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isInventoryDetailsAddedSuccessfully = res.data)
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(isError = true)
                    }
                }
            }
        }.launchIn(viewModelScope)


    }

    private fun updateState(details: InventoryDetails) {
        _uiState.update {
            it.copy(inventoryDetails = details, isValidInventory = hasAllData(details))
        }
    }

    private fun hasAllData(details: InventoryDetails): Boolean {
        return with(details) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}