package com.example.composearchsample.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composearchsample.data.local.DbConstants.USER_TABLE_NAME

@Entity(tableName = USER_TABLE_NAME)
data class LocalUser(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val password: String? = null
)