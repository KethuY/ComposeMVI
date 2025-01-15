package com.example.composearchsample.data.repos

import android.util.Log
import com.example.composearchsample.data.local.inventory.Inventory
import com.example.composearchsample.data.local.inventory.InventoryDao
import com.example.composearchsample.di.IoDispatcher
import com.example.composearchsample.network.ErrorResponse
import com.example.composearchsample.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Kethu on 26/06/2024.
 */
class InventoryRepositoryImpl @Inject constructor(
    private val localDataSource: InventoryDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : InventoryRepository {

    override fun addInventory(item: Inventory) = flow {
        try {
            emit(Result.Loading)
            if (item.id <= 0) {
                localDataSource.addInventory(item)
            } else {
                localDataSource.updateInventory(item)
            }
            emit(Result.Success(data = true))
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("404", "Inventory Item is not added")))
        }
    }.flowOn(dispatcher)

    override fun deleteInventory(item: Inventory) = flow {
        try {
            emit(Result.Loading)
            localDataSource.deleteInventory(item)
            emit(Result.Success(data = true))
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("404", "Inventory Item is not deleted")))
        }
    }.flowOn(dispatcher)

    override fun getInventory(id: Int): Flow<Result<Inventory>> = flow {
        try {
            emit(Result.Loading)
            val inventory = localDataSource.getInventory(id)
            emit(Result.Success(data = inventory))
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("404", "Inventory Item is not found")))
        }
    }.flowOn(dispatcher)

    override fun getInventories(): Flow<Result<List<Inventory>>> = flow {
        emit(Result.Loading)
        try {
            val inventories = localDataSource.getInventories()
            Log.d("Kethu", "getInventories:$inventories ")
            emit(Result.Success(data = inventories))
        } catch (e: Exception) {
            emit(Result.Success(data = emptyList()))
        }
    }.flowOn(dispatcher)
}