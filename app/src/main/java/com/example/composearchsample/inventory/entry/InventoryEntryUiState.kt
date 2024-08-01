package com.example.composearchsample.inventory.entry

import com.example.composearchsample.data.uimodels.InventoryDetails

data class InventoryEntryUiState(
    val inventoryDetails: InventoryDetails = InventoryDetails(),
    val isValidInventory: Boolean = false,
    val isInventoryDetailsAddedSuccessfully: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false
)


