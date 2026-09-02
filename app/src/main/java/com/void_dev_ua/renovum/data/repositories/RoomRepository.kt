package com.void_dev_ua.renovum.data.repositories

import com.void_dev_ua.renovum.data.local.daos.RoomDao
import com.void_dev_ua.renovum.domain.model.RoomEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomDao: RoomDao) {

	val allRooms: Flow<List<RoomEntity>> = roomDao.getAllRoomsFlow()

	suspend fun insert(room: RoomEntity) {
		roomDao.insertRoom(room)
	}

	suspend fun delete(room: RoomEntity) {
		roomDao.deleteRoom(room)
	}
}