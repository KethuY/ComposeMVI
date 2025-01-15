package com.example.composearchsample.data.local.inventory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composearchsample.data.local.DbConstants.INVENTORY_TABLE_NAME
import com.example.composearchsample.data.local.DbConstants.USER_TABLE_NAME
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = INVENTORY_TABLE_NAME)
data class Inventory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val price: Double,
    val quantity: Int
)