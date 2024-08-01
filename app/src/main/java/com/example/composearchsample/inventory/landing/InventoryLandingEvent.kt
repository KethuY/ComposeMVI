package com.example.composearchsample.inventory.landing

sealed class InventoryLandingEvent {
    data object GetInventories : InventoryLandingEvent()

}