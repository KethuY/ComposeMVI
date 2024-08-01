package com.example.composearchsample.data.repos

import com.example.composearchsample.data.uimodels.UserUiModel
import com.example.composearchsample.network.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by Kethu on 26/06/2024.
 */
interface UserRepository {
    suspend fun addUser(userName: String, password: String, email: String)

    suspend fun updateUser(user: UserUiModel)

    suspend fun deleteUser(id: String)

    fun getUser(id: String): UserUiModel

    fun getAllUsers(): List<UserUiModel>

    fun verifyUser(email: String, password: String): Flow<Result<Boolean>>
}