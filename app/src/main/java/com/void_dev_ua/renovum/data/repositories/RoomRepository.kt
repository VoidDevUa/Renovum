package com.void_dev_ua.renovum.data.repositories

import com.void_dev_ua.renovum.data.local.daos.RoomDao
import com.void_dev_ua.renovum.data.model.RoomEntity
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