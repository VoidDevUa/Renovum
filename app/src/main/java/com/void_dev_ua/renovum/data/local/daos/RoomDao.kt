package com.void_dev_ua.renovum.data.local.daos

import androidx.room.*
import com.void_dev_ua.renovum.model.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

	@Query("SELECT * FROM rooms")
	fun getAllRoomsFlow(): Flow<List<RoomEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertRoom(room: RoomEntity)

	@Delete
	suspend fun deleteRoom(room: RoomEntity)
}