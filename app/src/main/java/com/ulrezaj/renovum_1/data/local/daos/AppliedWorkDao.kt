package com.ulrezaj.renovum_1.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ulrezaj.renovum_1.data.model.AppliedWork
import kotlinx.coroutines.flow.Flow

@Dao
interface AppliedWorkDao {
	@Query("SELECT * FROM applied_works")
	fun getAllWorks(): Flow<List<AppliedWork>>

	@Query("DELETE FROM applied_works WHERE roomId = :roomId")
	suspend fun deleteByRoomId(roomId: String)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(work: AppliedWork)

	@Delete
	suspend fun delete(work: AppliedWork)

	@Update
	suspend fun update(work: AppliedWork)
}