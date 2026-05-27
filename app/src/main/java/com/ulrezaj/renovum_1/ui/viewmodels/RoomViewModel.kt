package com.ulrezaj.renovum_1.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.WorkCategory
import com.ulrezaj.renovum_1.data.model.WorkSection
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.data.model.WorkUnit
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
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

	private val _selectedRoom = mutableStateOf<RoomEntity?>(null)
	val selectedRoom: RoomEntity? get() = _selectedRoom.value
	var lastSelectedCategory by mutableStateOf<WorkCategory?>(null)

	var projectDiscountPercent by mutableDoubleStateOf(0.0)
		private set

	var showDiscountDialog by mutableStateOf(false)

	var workToEdit by mutableStateOf<AppliedWork?>(null)

	fun updateDiscount(newPercent: Double) {
		projectDiscountPercent = newPercent.coerceIn(0.0, 100.0)
		L.d("ViewModel: Discount updated to $projectDiscountPercent%")
	}

	/**
	 * Сума всіх робіт БЕЗ знижки
	 */
	fun getTotalRawSum(): Double {
		return _appliedWorks.sumOf { it.priceAtTime * it.quantity }
	}

	/**
	 * Сума всіх робіт З урахуванням знижки
	 */
	fun getTotalDiscountedSum(): Double {
		val raw = getTotalRawSum()
		return raw * (1.0 - projectDiscountPercent / 100.0)
	}

	fun selectRoom(room: RoomEntity) {
		_selectedRoom.value = room
		L.d("ViewModel: Room selected -> ${room.name}")
	}

	fun addRoom(room: RoomEntity) {
		_rooms.add(room)
		if (_selectedRoom.value == null) _selectedRoom.value = room
		L.d("ViewModel: Room added: ${room.name}. Total: ${_rooms.size}")
	}
	fun deleteRoom(room: RoomEntity) {
		val removed = _rooms.remove(room)
		if (removed) {
			_appliedWorks.removeAll { it.roomId == room.id }

			L.d("ViewModel: Room ${room.name} and its works deleted. Remaining rooms: ${_rooms.size}")

			if (_selectedRoom.value?.id == room.id) {
				_selectedRoom.value = _rooms.firstOrNull()
			}
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

	/**
	 * Групує виконані роботи по кімнатах.
	 * Повертає Map, де ключ — Кімната, а значення — список пар (Виконана робота, Опис послуги)
	 */
	fun getGroupedWorks(): Map<RoomEntity, List<Pair<AppliedWork, WorkService>>> {
		return _appliedWorks.groupBy { work ->
			_rooms.find { it.id == work.roomId }
		}
			.filterKeys { it != null }
			.mapKeys { it.key!! }
			.mapValues { entry ->
				entry.value.map { applied ->
					val service = WorkDataRepository.allWorks.find { it.id == applied.workId }
						?: WorkService(
							id = applied.workId,
							section = WorkSection.FINISHING,
							category = WorkCategory.PAINTING,
							name = "Невідома робота",
							unit = WorkUnit.M2,
							minPrice = 0.0,
							maxPrice = 0.0,
							averagePrice = 0.0
						)
					applied to service
				}
			}
	}

	/**
	 * Загальна сума всіх робіт у проєкті
	 */
	fun getTotalProjectSum(): Double {
		return _appliedWorks.sumOf { it.priceAtTime * it.quantity }
	}

	fun isWorkDone(workId: String, roomId: String): Boolean {
		return _appliedWorks.any { it.workId == workId && it.roomId == roomId }
	}

	/**
	 * Оновлює існуючу роботу в списку
	 */
	fun updateAppliedWork(originalWork: AppliedWork, newPrice: Double, newQuantity: Double) {
		val index = _appliedWorks.indexOf(originalWork)
		if (index != -1) {
			_appliedWorks[index] = originalWork.copy(
				priceAtTime = newPrice,
				quantity = newQuantity
			)
			L.d("ViewModel: Updated work at index $index. New total: ${newPrice * newQuantity}")
		}
	}

	/**
	 * Видаляє виконану роботу (якщо раптом у діалозі захочеш додати кнопку видалення)
	 */
	fun deleteAppliedWork(work: AppliedWork) {
		if (_appliedWorks.remove(work)) {
			L.d("ViewModel: Removed applied work: ${work.workId}")
		}
	}

	fun getWorkServiceById(workId: String): WorkService? {
		// Тут логіка пошуку у твоєму загальному списку робіт
		return null
	}
}