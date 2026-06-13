package com.void_dev_ua.renovum.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.void_dev_ua.renovum.data.local.daos.AppliedWorkDao
import com.void_dev_ua.renovum.data.local.daos.RoomDao
import com.void_dev_ua.renovum.model.AppliedWork
import com.void_dev_ua.renovum.model.RoomEntity

@Database(entities = [RoomEntity::class, AppliedWork::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun roomDao(): RoomDao
	abstract fun appliedWorkDao(): AppliedWorkDao

	companion object {
		@Volatile
		private var INSTANCE: AppDatabase? = null

		fun getDatabase(context: Context): AppDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					AppDatabase::class.java,
					"renovum_database"
				)
					.fallbackToDestructiveMigration(true)
					.build()
				INSTANCE = instance
				instance
			}
		}
	}
}