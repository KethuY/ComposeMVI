package com.example.composearchsample.di

import android.content.Context
import androidx.room.Room
import com.example.composearchsample.data.datastore.DataStoreRepository
import com.example.composearchsample.data.datastore.DataStoreRepositoryImpl
import com.example.composearchsample.data.local.DbConstants.INVENTORY_DATABASE_NAME
import com.example.composearchsample.data.local.DbConstants.USER_DATABASE_NAME
import com.example.composearchsample.data.local.inventory.InventoryDao
import com.example.composearchsample.data.local.inventory.InventoryDataBase
import com.example.composearchsample.data.local.user.UserDao
import com.example.composearchsample.data.local.user.UserDataBase
import com.example.composearchsample.data.network.NetworkDataSource
import com.example.composearchsample.data.network.UserNetworkDataSourceImpl
import com.example.composearchsample.data.repos.InventoryRepository
import com.example.composearchsample.data.repos.InventoryRepositoryImpl
import com.example.composearchsample.data.repos.UserRepository
import com.example.composearchsample.data.repos.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindInventoryRepository(repository: InventoryRepositoryImpl): InventoryRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: UserNetworkDataSourceImpl): NetworkDataSource
}


@Module
@InstallIn(ViewModelComponent::class)
 class DataStoreModule {
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository = DataStoreRepositoryImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideUserDataBase(@ApplicationContext context: Context): UserDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDataBase::class.java,
            USER_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(database: UserDataBase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideInventoryDataBase(@ApplicationContext context: Context): InventoryDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            InventoryDataBase::class.java,
            INVENTORY_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideInventoryDao(database: InventoryDataBase): InventoryDao = database.inventoryDao()

}
