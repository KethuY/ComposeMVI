package com.example.composearchsample.data.network

import com.example.composearchsample.data.local.user.UserDao
import com.example.composearchsample.di.ApplicationScope
import com.example.composearchsample.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class UserNetworkDataSourceImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
    private val localDataSource: UserDao,
    ) : NetworkDataSource {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()
    private var users = listOf(
        NetworkUser(
            id = "223",
            name = "William",
            email = "Ground looks good, no foundation work required."
        ),
        NetworkUser(
            id = "223",
            name = "William",
            email = "Ground looks good, no foundation work required."
        )
    )

    override suspend fun getUsers(): List<NetworkUser> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return users
    }

    override suspend fun saveUsers(newUsers: List<NetworkUser>) = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        users = newUsers
    }
}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
