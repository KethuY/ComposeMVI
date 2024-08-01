package com.example.composearchsample.data.repos

import com.example.composearchsample.data.local.inventory.Inventory
import kotlinx.coroutines.flow.Flow
import com.example.composearchsample.network.Result


/**
 * Created by Kethu on 26/06/2024.
 */
interface InventoryRepository {

     fun addInventory(item: Inventory): Flow<Result<Boolean>>

     fun updateInventory(item: Inventory): Flow<Result<Boolean>>

     fun deleteInventory(item: Inventory): Flow<Result<Boolean>>

    fun getInventory(id: Int): Flow<Result<Inventory>>

    fun getInventories(): Flow<Result<List<Inventory>>>

}