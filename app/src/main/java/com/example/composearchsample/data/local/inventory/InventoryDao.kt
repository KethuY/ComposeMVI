package com.example.composearchsample.data.local.inventory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.composearchsample.data.local.DbConstants.INVENTORIES_QUERY_BY_ID
import com.example.composearchsample.data.local.DbConstants.INVENTORIES_QUERY_BY_NAME_IN_ASC
import com.example.composearchsample.data.local.DbConstants.USER_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInventory(item: Inventory)

    @Update
    suspend fun updateInventory(item: Inventory)

    @Delete
    suspend fun deleteInventory(item: Inventory)

    @Query(INVENTORIES_QUERY_BY_ID)
    fun getInventory(id: Int): Inventory

    @Query(INVENTORIES_QUERY_BY_NAME_IN_ASC)
    fun getInventories(): List<Inventory>
}