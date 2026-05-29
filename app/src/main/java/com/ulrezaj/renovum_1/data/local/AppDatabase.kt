package com.ulrezaj.renovum_1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ulrezaj.renovum_1.data.local.daos.AppliedWorkDao
import com.ulrezaj.renovum_1.data.local.daos.RoomDao
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.RoomEntity

@Database(entities = [RoomEntity::class, AppliedWork::class], version = 1, exportSchema = false)
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
					.fallbackToDestructiveMigration(false)
					.build()
				INSTANCE = instance
				instance
			}
		}
	}
}