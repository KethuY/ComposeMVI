package com.example.composearchsample.data.local.user

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Kethu on 26/06/2024.
 */
@Database(entities = [LocalUser::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}