package com.ulrezaj.renovum_1.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.utility.L

class RoomViewModel : ViewModel() {
	private val _rooms = mutableStateListOf<RoomEntity>()
	val rooms: List<RoomEntity> get() = _rooms

	fun addRoom(room: RoomEntity) {
		_rooms.add(room)
		L.d("ViewModel: Room added: ${room.name} (ID: ${room.id}). Total rooms: ${_rooms.size}")
	}

	fun deleteRoom(room: RoomEntity) {
		val removed = _rooms.remove(room)
		if (removed) {
			L.d("ViewModel: Room deleted: ${room.name}. Remaining: ${_rooms.size}")
		} else {
			L.e("ViewModel: Failed to delete room: ${room.name} (Not found in list)")
		}
	}
}