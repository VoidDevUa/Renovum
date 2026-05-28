package com.ulrezaj.renovum_1.data.repositories

import com.ulrezaj.renovum_1.data.local.daos.RoomDao
import com.ulrezaj.renovum_1.data.model.RoomEntity
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val roomDao: RoomDao) {

	val allRooms: Flow<List<RoomEntity>> = roomDao.getAllRoomsFlow()

	suspend fun insert(room: RoomEntity) {
		roomDao.insertRoom(room)
	}

	suspend fun delete(room: RoomEntity) {
		roomDao.deleteRoom(room)
	}
}