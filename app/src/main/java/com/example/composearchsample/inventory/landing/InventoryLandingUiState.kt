package com.example.composearchsample.inventory.landing

import com.example.composearchsample.data.local.inventory.Inventory

data class InventoryLandingUiState(
    val inventories: List<Inventory> = emptyList(),
    val isLoading: Boolean = false
)
