package com.example.composearchsample.data.repos

import com.example.composearchsample.data.local.inventory.Inventory
import com.example.composearchsample.data.local.inventory.InventoryDao
import com.example.composearchsample.di.ApplicationScope
import com.example.composearchsample.di.IoDispatcher
import com.example.composearchsample.network.ErrorResponse
import com.example.composearchsample.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Kethu on 26/06/2024.
 */
class InventoryRepositoryImpl @Inject constructor(
    private val localDataSource: InventoryDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : InventoryRepository {
    override fun addInventory(item: Inventory) = flow {
        emit(Result.Loading)
        try {
            scope.launch {
                localDataSource.addInventory(item)
                emit(Result.Success(data = true))
            }
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("404", "Inventory Item is not added")))
        }
    }.flowOn(dispatcher)


    override fun updateInventory(item: Inventory) = flow {
        emit(Result.Loading)
        try {
            scope.launch {
                localDataSource.updateInventory(item)
                emit(Result.Success(data = true))
            }
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("400", "Inventory Item is not updated")))
        }
    }.flowOn(dispatcher)

    override fun deleteInventory(item: Inventory) = flow {
        emit(Result.Loading)
        try {
            scope.launch {
                localDataSource.deleteInventory(item)
                emit(Result.Success(data = true))
            }
        } catch (e: Exception) {
            emit(Result.Error(error = ErrorResponse("404", "Inventory Item is not deleted")))
        }
    }.flowOn(dispatcher)

    override fun getInventory(id: Int): Flow<Result<Inventory>> = flow {
        emit(Result.Loading)
        try {
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
            emit(Result.Success(data = inventories))
        } catch (e: Exception) {
            emit(Result.Success(data = emptyList()))
        }
    }.flowOn(dispatcher)

}