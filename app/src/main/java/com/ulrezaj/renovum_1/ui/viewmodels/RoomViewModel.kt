package com.ulrezaj.renovum_1.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulrezaj.renovum_1.data.UserSettings
import com.ulrezaj.renovum_1.data.model.AppliedWork
import com.ulrezaj.renovum_1.data.model.CalculatedData
import com.ulrezaj.renovum_1.data.model.ReportData
import com.ulrezaj.renovum_1.data.model.RoomEntity
import com.ulrezaj.renovum_1.data.model.TargetSurface
import com.ulrezaj.renovum_1.data.model.WorkCategory
import com.ulrezaj.renovum_1.data.model.WorkSection
import com.ulrezaj.renovum_1.data.model.WorkService
import com.ulrezaj.renovum_1.data.model.WorkUnit
import com.ulrezaj.renovum_1.data.repositories.RoomRepository
import com.ulrezaj.renovum_1.data.repositories.WordExportManager
import com.ulrezaj.renovum_1.data.repositories.WorkDataRepository
import com.ulrezaj.renovum_1.data.repositories.WorkRepository
import com.ulrezaj.renovum_1.utility.L
import com.ulrezaj.renovum_1.utility.RenovumNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SdCardPath")
class RoomViewModel(
	private val roomRepository: RoomRepository,
	private val workRepository: WorkRepository
) : ViewModel() {
	private val _rooms = mutableStateListOf<RoomEntity>()
	val rooms: List<RoomEntity> get() = _rooms

	val archiveFiles = mutableStateListOf<File>()
	val selectedArchiveFiles = mutableStateListOf<File>()
	var isArchiveSelectMode by mutableStateOf(false)

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

	val totalRawSumState: StateFlow<Double> = appliedWorks
		.map { worksList -> worksList.sumOf { it.priceAtTime * it.quantity } }
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Eagerly,
			initialValue = 0.0
		)

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
		}.stateIn(
			viewModelScope,
			SharingStarted.WhileSubscribed(5000),
			emptyMap()
		)

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
	fun getTotalRawSum(): Double = totalRawSumState.value
	fun getTotalDiscountedSum(): Double {
		return totalRawSumState.value * (1.0 - projectDiscountPercent / 100.0)
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
	fun deleteAppliedWork(work: AppliedWork) {
		viewModelScope.launch {
			workRepository.delete(work)
			L.d("ViewModel: Removed from DB: ${work.workId}")
		}
	}
	fun updateAppliedWork(originalWork: AppliedWork, newPrice: Double, newQuantity: Double) {
		viewModelScope.launch {
			val updatedWork = originalWork.copy(priceAtTime = newPrice, quantity = newQuantity)
			workRepository.update(updatedWork)
			L.d("ViewModel: Updated in DB. New total: ${newPrice * newQuantity}")
		}
	}

	fun generateWordReportInBackground(
		context: Context,
		isGroupedByRooms: Boolean,
		targetAddress: String,
		customFileName: String,
		userSettings: UserSettings
	) {
		val appContext = context.applicationContext
		val notificationId = System.currentTimeMillis().hashCode()

		viewModelScope.launch {
			RenovumNotificationManager.showProgressNotification(appContext, notificationId)
			Toast.makeText(appContext, "Формування файлу кошторису...", Toast.LENGTH_SHORT).show()

			val wordFile = withContext(Dispatchers.IO) {
				try {
					val locale = appContext.resources.configuration.locales[0] ?: Locale.getDefault()
					val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)
					val currentDateString = dateFormat.format(Date())

					val reportData = ReportData(
						projectName = targetAddress,
						dateString = currentDateString,
						roomsWithWorks = groupedWorksState.value,
						totalRawSum = getTotalRawSum(),
						discountPercent = projectDiscountPercent,
						totalDiscountedSum = getTotalDiscountedSum()
					)

					WordExportManager.createWordDocument(
						context = appContext,
						data = reportData,
						isGroupedByRooms = isGroupedByRooms,
						customFileName = customFileName,
						userSettings = userSettings
					)
				} catch (e: Exception) {
					L.e("RoomViewModel: Помилка генерації документа", e)
					null
				}
			}

			if (wordFile != null && wordFile.exists()) {
				L.d("RoomViewModel: Фоновий файл успішно створено!")
				Toast.makeText(appContext, "Файл-кошторис створено успішно!", Toast.LENGTH_LONG).show()
				RenovumNotificationManager.showSuccessNotification(appContext, wordFile, notificationId)
			} else {
				Toast.makeText(appContext, "Не вдалося згенерувати файл", Toast.LENGTH_SHORT).show()
				RenovumNotificationManager.cancelExportNotification(appContext, notificationId)
			}
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
				withContext(Dispatchers.Main) {
					updateDiscount(0.0)
				}
				L.d("ViewModel: Поточний об'єкт успішно очищено")
			} catch (e: Exception) {
				L.e("ViewModel: Помилка повного очищення об'єкта", e)
			}
		}
	}

	/**
	 * Зчитує всі файли .docx із локального архіву додатка
	 */
	fun loadArchiveFiles(context: Context) {
		val appContext = context.applicationContext
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val archiveDir = File(appContext.filesDir, "Archive")
				if (archiveDir.exists()) {
					val files = archiveDir.listFiles { _, name -> name.endsWith(".docx") }

					withContext(Dispatchers.Main) {
						archiveFiles.clear()
						if (files != null) {
							archiveFiles.addAll(files.sortedByDescending { it.lastModified() })
						}
					}
				} else {
					withContext(Dispatchers.Main) { archiveFiles.clear() }
				}
			} catch (e: Exception) {
				L.e("RoomViewModel: Помилка завантаження файлів архіву", e)
			}
		}
	}

	/**
	 * Перемикає виділення файлу (для мультивибору)
	 */
	fun toggleArchiveFileSelection(file: File) {
		if (selectedArchiveFiles.contains(file)) {
			selectedArchiveFiles.remove(file)
			if (selectedArchiveFiles.isEmpty()) {
				isArchiveSelectMode = false
			}
		} else {
			selectedArchiveFiles.add(file)
			isArchiveSelectMode = true
		}
	}

	/**
	 * Очищає виділення та виходить з режиму вибору
	 */
	fun clearArchiveSelection() {
		selectedArchiveFiles.clear()
		isArchiveSelectMode = false
	}

	/**
	 * Видаляє всі виділені файли з пам'яті пристрою та оновлює список
	 */
	fun deleteSelectedArchiveFiles(context: Context) {
		val appContext = context.applicationContext
		viewModelScope.launch(Dispatchers.IO) {
			try {
				selectedArchiveFiles.forEach { file ->
					if (file.exists()) {
						val deleted = file.delete()
						if (deleted) {
							L.d("RoomViewModel: Файл ${file.name} видалено")
						}
					}
				}
				withContext(Dispatchers.Main) {
					clearArchiveSelection()
					loadArchiveFiles(appContext)
				}
			} catch (e: Exception) {
				L.e("RoomViewModel: Помилка при видаленні файлів", e)
			}
		}
	}
}