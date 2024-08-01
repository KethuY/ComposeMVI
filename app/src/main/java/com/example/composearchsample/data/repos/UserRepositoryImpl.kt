package com.example.composearchsample.data.repos

import com.example.composearchsample.data.local.user.LocalUser
import com.example.composearchsample.data.local.user.UserDao
import com.example.composearchsample.data.network.NetworkDataSource
import com.example.composearchsample.data.uimodels.UserUiModel
import com.example.composearchsample.di.ApplicationScope
import com.example.composearchsample.di.IoDispatcher
import com.example.composearchsample.network.ErrorResponse
import com.example.composearchsample.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


/**
 * Created by Kethu on 26/06/2024.
 */
class UserRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: UserDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : UserRepository {
    override suspend fun addUser(userName: String, password: String, email: String) {
        val userId = withContext(dispatcher) {
            UUID.randomUUID().toString()
        }
        val user = LocalUser(
            id = userId,
            name = userName,
            password = password,
            email = email
        )
        localDataSource.upsertUser(user)
        saveTasksToNetwork()
    }

    override suspend fun updateUser(user: UserUiModel) {
        localDataSource.upsertUser(user.toLocal())
    }

    override suspend fun deleteUser(id: String) {
        localDataSource.deleteUser(id)
    }

    override fun getUser(id: String) = localDataSource.getUser(id).toUiUserModel()

    override fun getAllUsers() = localDataSource.getAllUsers().toUiUserModels()
    override fun verifyUser(email: String, password: String) = flow {
        emit(Result.Loading)
        val user = localDataSource.getUserFromEmail(email)
        val isValidUser = user?.password == password
        if (isValidUser) {
            emit(Result.Success(data = true))
        } else {
            emit(Result.Error(error = ErrorResponse("404", "User not found")))
        }
    }.flowOn(dispatcher)

    private fun saveTasksToNetwork() {
        scope.launch {
            try {
                val localUsers = localDataSource.getAllUsers()
                val networkTasks = withContext(dispatcher) {
                    localUsers.toNetwork()
                }
                networkDataSource.saveUsers(networkTasks)
            } catch (e: Exception) {
                // In a real app you'd handle the exception e.g. by exposing a `networkStatus` flow
                // to an app level UI state holder which could then display a Toast message.
            }
        }
    }
}