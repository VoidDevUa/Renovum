package com.ulrezaj.renovum_1.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.utility.L

data class CalculatedData(
	val floorArea: Double,
	val wallArea: Double,
	val perimeter: Double,
	val extra: Map<String, Double>
)

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

	fun calculateRoomData(room: RoomEntity): CalculatedData {
		val p = room.params
		val floorArea = p.getFloorArea()
		val perimeter = p.getPerimeter()

		val openingsArea = room.openings.sumOf { it.width.toDouble() * it.height.toDouble() }
		val wallArea = (perimeter * p.roomHeight) - openingsArea

		return CalculatedData(floorArea, wallArea, perimeter, p.getExtraResults())
	}
}