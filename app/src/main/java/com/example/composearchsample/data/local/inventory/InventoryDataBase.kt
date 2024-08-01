package com.example.composearchsample.data.local.inventory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Inventory::class], version = 1, exportSchema = false)
abstract class InventoryDataBase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}