package com.example.composearchsample.data.network

interface NetworkDataSource {

    suspend fun getUsers(): List<NetworkUser>

    suspend fun saveUsers(newUsers: List<NetworkUser>)
}
