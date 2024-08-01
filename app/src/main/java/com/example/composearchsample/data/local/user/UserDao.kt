package com.example.composearchsample.data.local.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.composearchsample.data.local.DbConstants.USER_TABLE_NAME

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: LocalUser):Long

    @Query("DELETE FROM $USER_TABLE_NAME WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Query("SELECT * from $USER_TABLE_NAME WHERE id = :id")
    fun getUser(id: String): LocalUser

    @Query("SELECT * from $USER_TABLE_NAME ORDER BY name ASC")
    fun getAllUsers(): List<LocalUser>

    @Query("SELECT * from $USER_TABLE_NAME WHERE email = :email")
    fun getUserFromEmail(email: String): LocalUser?
}