package com.void_dev_ua.renovum.di

import android.content.Context
import com.void_dev_ua.renovum.data.local.AppDatabase
import com.void_dev_ua.renovum.data.local.daos.AppliedWorkDao
import com.void_dev_ua.renovum.data.local.daos.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideRoomDao(database: AppDatabase): RoomDao {
        return database.roomDao()
    }

    @Provides
    fun provideAppliedWorkDao(database: AppDatabase): AppliedWorkDao {
        return database.appliedWorkDao()
    }
}
