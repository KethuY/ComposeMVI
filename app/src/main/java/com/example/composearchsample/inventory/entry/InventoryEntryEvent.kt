package com.example.composearchsample.inventory.entry

import com.example.composearchsample.data.uimodels.InventoryDetails

sealed class InventoryEntryEvent {
    data class UpdateUiState(val inventoryDetails: InventoryDetails) : InventoryEntryEvent()
    data object AddInventory: InventoryEntryEvent()
}