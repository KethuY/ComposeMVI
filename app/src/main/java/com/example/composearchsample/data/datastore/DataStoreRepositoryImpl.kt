package com.example.composearchsample.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.composearchsample.data.datastore.DataStoreConstants.DATA_STORE_NAME
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) : DataStoreRepository {
    private val dataSource = context.dataStore
    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T)=
        dataSource.data.first()[key] ?: defaultValue

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun <T> clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }
}
