package com.ulrezaj.renovum_1.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.ulrezaj.renovum_1.data.model.RoomEntity

class RoomViewModel : ViewModel() {
	private val _rooms = mutableStateListOf<RoomEntity>()
	val rooms: List<RoomEntity> get() = _rooms

	fun addRoom(room: RoomEntity) {
		_rooms.add(room)
	}

	fun deleteRoom(room: RoomEntity) {
		_rooms.remove(room)
	}
}