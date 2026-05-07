package com.ulrezaj.renovum_1.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.WorkService
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

	private val _appliedWorks = mutableStateListOf<AppliedWork>()
	val appliedWorks: List<AppliedWork> get() = _appliedWorks

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

	fun saveDoneWork(
		room: RoomEntity,
		work: WorkService,
		price: Double,
		quantity: Double
	) {
		val newWork = AppliedWork(
			workId = work.id,
			roomId = room.id,
			priceAtTime = price,
			quantity = quantity
		)
		_appliedWorks.add(newWork)
		L.d("ViewModel: Saved ${work.name} for ${room.name}. Qty: $quantity")
	}

	fun isWorkDone(workId: String, roomId: String): Boolean {
		return _appliedWorks.any { it.workId == workId && it.roomId == roomId }
	}

	fun getWorkServiceById(workId: String): WorkService? {
		// Тут логіка пошуку в твоєму загальному списку робіт
		return null
	}
}