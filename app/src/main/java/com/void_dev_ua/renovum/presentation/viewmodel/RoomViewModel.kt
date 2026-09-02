package com.void_dev_ua.renovum.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.CalculatedData
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.TargetSurface
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.domain.usecase.CalculationOptionType
import com.void_dev_ua.renovum.domain.usecase.RoomCalculationsUseCase
import com.void_dev_ua.renovum.core.util.L
import com.void_dev_ua.renovum.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("SdCardPath")
class RoomViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private val roomRepository: RoomRepository,
	private val workRepository: WorkRepository,
	private val roomCalculationsUseCase: RoomCalculationsUseCase
) : ViewModel() {
	private val _rooms = mutableStateListOf<RoomEntity>()
	val rooms: List<RoomEntity> get() = _rooms

	private val _selectedRoom = mutableStateOf<RoomEntity?>(null)
	val selectedRoom: State<RoomEntity?> = _selectedRoom

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
		return roomCalculationsUseCase.calculateRoomData(room)
	}

	fun getSurfaceValue(target: TargetSurface, calcData: CalculatedData): Double {
		return roomCalculationsUseCase.getSurfaceValue(target, calcData)
	}

	fun getAvailableOptions(target: TargetSurface, calcData: CalculatedData): List<Pair<String, Double>> {
		val rawOptions = roomCalculationsUseCase.getAvailableOptions(target, calcData)
		return rawOptions.map { (type, value) ->
			val label = when (type) {
				CalculationOptionType.FLOOR -> context.getString(R.string.surface_floor)
				CalculationOptionType.WALLS_CLEAN -> context.getString(R.string.surface_walls_clean)
				CalculationOptionType.WALLS_GROSS -> context.getString(R.string.surface_walls_gross)
				CalculationOptionType.PERIMETER -> context.getString(R.string.surface_perimeter)
			}
			"$label: ${"%.1f".format(value)}" to value
		}
	}

	/**
	 * Повністю очищує поточний проєкт: видаляє всі кімнати, роботи та адресу об'єкта
	 */
	fun clearCurrentProject(onClearAddress: suspend () -> Unit) {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_rooms.forEach { room ->
					workRepository.deleteWorksByRoomId(room.id)
				}
				_rooms.forEach { room ->
					roomRepository.delete(room)
				}
				onClearAddress()
				L.d("ViewModel: Поточний об'єкт успішно очищено")
			} catch (e: Exception) {
				L.e("ViewModel: Помилка повного очищення об'єкта", e)
			}
		}
	}
}
