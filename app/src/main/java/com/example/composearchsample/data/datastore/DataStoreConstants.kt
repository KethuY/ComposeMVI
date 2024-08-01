package com.example.composearchsample.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

/**
 * Created by Kethu on 27/06/2024.
 */
object DataStoreConstants {
    const val DATA_STORE_NAME = "my_data_store"
    val IS_USER_SIGNED_IN = booleanPreferencesKey("is_user_logged_in")
    val IS_NEW_USER = booleanPreferencesKey("is_new_user")
}