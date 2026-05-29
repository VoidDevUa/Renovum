package com.ulrezaj.renovum_1.ui.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.TargetSurface
import com.ulrezaj.renovum_1.data.model.WorkCategory
import com.ulrezaj.renovum_1.data.model.WorkSection
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.data.model.WorkUnit
import com.ulrezaj.renovum_1.data.repositories.RoomRepository
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
import com.ulrezaj.renovum_1.data.repositories.WorkRepository
import com.ulrezaj.renovum_1.utility.L
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CalculatedData(
	val floorArea: Double,
	val wallArea: Double,
	val cleanWallArea: Double,
	val allOpeningsArea: Double,
	val perimeter: Double,
	val extra: Map<String, Double>
)

@SuppressLint("SdCardPath")
class RoomViewModel(
	private val roomRepository: RoomRepository,
	private val workRepository: WorkRepository
) : ViewModel() {
	private val _rooms = mutableStateListOf<RoomEntity>()
	val rooms: List<RoomEntity> get() = _rooms

	val appliedWorks: StateFlow<List<AppliedWork>> = workRepository.allWorks
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = emptyList()
		)

	private val _selectedRoom = mutableStateOf<RoomEntity?>(null)
	val selectedRoom: State<RoomEntity?> = _selectedRoom

	var lastSelectedCategory by mutableStateOf<WorkCategory?>(null)
	var projectDiscountPercent by mutableDoubleStateOf(0.0)
		private set
	var showDiscountDialog by mutableStateOf(false)
	var workToEdit by mutableStateOf<AppliedWork?>(null)

	init {
		viewModelScope.launch {
			roomRepository.allRooms.collect { dbRooms ->
				_rooms.clear()
				_rooms.addAll(dbRooms)
				L.d("ViewModel: Synced rooms from DB. Total: ${_rooms.size}")

				if (_selectedRoom.value == null || dbRooms.none { it.id == _selectedRoom.value?.id }) {
					_selectedRoom.value = dbRooms.firstOrNull()
				} else {
					val updated = dbRooms.find { it.id == _selectedRoom.value?.id }
					if (updated != _selectedRoom.value) {
						_selectedRoom.value = updated
					}
				}
			}
		}
	}

	fun updateDiscount(newPercent: Double) {
		projectDiscountPercent = newPercent.coerceIn(0.0, 100.0)
		L.d("ViewModel: Discount updated to $projectDiscountPercent%")
	}

	/**
	 * Сума всіх робіт БЕЗ знижки
	 */
	fun getTotalRawSum(): Double {
		return appliedWorks.value.sumOf { it.priceAtTime * it.quantity }
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
		viewModelScope.launch {
			roomRepository.insert(room)
			L.d("ViewModel: Triggered DB insert for room: ${room.name}")
		}
	}
	fun deleteRoom(room: RoomEntity) {
		viewModelScope.launch {
			workRepository.deleteWorksByRoomId(room.id)
			roomRepository.delete(room)
			L.d("ViewModel: Successfully deleted room ${room.name} and its works from DB")
		}
	}

	fun calculateRoomData(room: RoomEntity): CalculatedData {
		val p = room.params
		val floorArea = p.getFloorArea()
		val perimeter = p.getPerimeter()

		val openingsArea = room.openings.sumOf { it.width.toDouble() * it.height.toDouble() }
		val wallArea = (perimeter * p.roomHeight)
		val cleanWallArea = wallArea - openingsArea

		return CalculatedData(floorArea, wallArea, cleanWallArea, openingsArea, perimeter, p.getExtraResults())
	}

	fun getSurfaceValue(target: TargetSurface, calcData: CalculatedData): Double {
		return when (target) {
			TargetSurface.FLOOR_AREA -> calcData.floorArea
			TargetSurface.WALL_CLEAN_AREA -> calcData.cleanWallArea
			TargetSurface.WALL_GROSS_AREA -> calcData.wallArea
			TargetSurface.CEILING_AREA -> calcData.floorArea
			TargetSurface.ROOM_PERIMETER -> calcData.perimeter
			TargetSurface.ANY_SQUARE_METER, TargetSurface.ANY_RUNNING_METER -> 1.0
			TargetSurface.NONE -> 0.0
		}
	}
	fun getAvailableOptions(target: TargetSurface, calcData: CalculatedData): List<Pair<String, Double>> {
		val options = mutableListOf<Pair<String, Double>>()

		when (target) {
			TargetSurface.ANY_SQUARE_METER -> {
				options.add("Підлога: ${"%.1f".format(calcData.floorArea)}" to calcData.floorArea)
				options.add("Стіни (чист.): ${"%.1f".format(calcData.cleanWallArea)}" to calcData.cleanWallArea)
				options.add("Стіни (заг.): ${"%.1f".format(calcData.wallArea)}" to calcData.wallArea)
			}

			TargetSurface.ANY_RUNNING_METER -> {
				options.add("Периметр: ${"%.1f".format(calcData.perimeter)}" to calcData.perimeter)
			}

			else -> {}
		}

		return options
	}

	fun saveAppliedWork(room: RoomEntity, work: WorkService, price: Double, quantity: Double) {
		val newWork = AppliedWork(
			workId = work.id,
			roomId = room.id,
			quantity = quantity,
			priceAtTime = price
		)
		viewModelScope.launch {
			workRepository.insert(newWork)
			L.d("ViewModel: Saved ${work.name} to DB for ${room.name}")
		}
	}

	/**
	 * Видаляє виконану роботу
	 */
	fun deleteAppliedWork(work: AppliedWork) {
		viewModelScope.launch {
			workRepository.delete(work)
			L.d("ViewModel: Removed from DB: ${work.workId}")
		}
	}

	/**
	 * Оновлює існуючу роботу в списку
	 */
	fun updateAppliedWork(originalWork: AppliedWork, newPrice: Double, newQuantity: Double) {
		viewModelScope.launch {
			val updatedWork = originalWork.copy(priceAtTime = newPrice, quantity = newQuantity)
			workRepository.update(updatedWork)
			L.d("ViewModel: Updated in DB. New total: ${newPrice * newQuantity}")
		}
	}

	/**
	 * Групує виконані роботи по кімнатах.
	 * Повертає Map, де ключ — Кімната, а значення — список пар (Виконана робота, Опис послуги)
	 */
	val groupedWorksState: StateFlow<Map<RoomEntity, List<Pair<AppliedWork, WorkService>>>> =
		appliedWorks.map { worksList ->
			worksList.groupBy { work ->
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
		}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

	/**
	 * Загальна сума всіх робіт у проєкті
	 */
	fun getTotalProjectSum(): Double {
		return appliedWorks.value.sumOf { it.priceAtTime * it.quantity }
	}

	fun isWorkDone(workId: String, roomId: String): Boolean {
		return appliedWorks.value.any { it.workId == workId && it.roomId == roomId }
	}

	fun getWorkServiceById(workId: String): WorkService? {
		// Тут логіка пошуку у твоєму загальному списку робіт
		return null
	}
}