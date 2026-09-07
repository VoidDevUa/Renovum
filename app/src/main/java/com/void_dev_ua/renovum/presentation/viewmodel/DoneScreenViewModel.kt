package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.WorkCategory
import com.void_dev_ua.renovum.domain.model.WorkSection
import com.void_dev_ua.renovum.domain.model.WorkService
import com.void_dev_ua.renovum.domain.model.WorkUnit
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoneScreenViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val workRepository: WorkRepository,
    private val workDataRepository: WorkDataRepository,
    private val projectStateRepository: ProjectStateRepository
) : ViewModel() {

    val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val appliedWorks = workRepository.allWorks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val groupedWorksState: StateFlow<Map<RoomEntity, List<Pair<AppliedWork, WorkService>>>> =
        combine(appliedWorks, workDataRepository.allWorksFlow, rooms) { worksList, allServices, roomList ->
            worksList.groupBy { work ->
                roomList.find { it.id == work.roomId }
            }
                .filterKeys { it != null }
                .mapKeys { it.key!! }
                .mapValues { entry ->
                    entry.value.map { applied ->
                        val service = allServices.find { it.id == applied.workId }
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

    val totalRawSumState: StateFlow<Double> = appliedWorks
        .map { worksList -> worksList.sumOf { it.priceAtTime * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val projectDiscountPercent = projectStateRepository.globalDiscount

    val totalDiscountedSumState: StateFlow<Double> = combine(totalRawSumState, projectDiscountPercent) { rawSum, discount ->
        rawSum * (1.0 - discount / 100.0)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    var showDiscountDialog by mutableStateOf(false)
    var workToEdit by mutableStateOf<AppliedWork?>(null)

    init {
        viewModelScope.launch {
            workDataRepository.loadWorks()
        }
    }

    fun updateDiscount(newPercent: Double) {
        projectStateRepository.setGlobalDiscount(newPercent)
    }

    fun updateAppliedWork(originalWork: AppliedWork, newPrice: Double, newQuantity: Double) {
        viewModelScope.launch {
            val updatedWork = originalWork.copy(priceAtTime = newPrice, quantity = newQuantity)
            workRepository.update(updatedWork)
        }
    }

    fun deleteAppliedWork(work: AppliedWork) {
        viewModelScope.launch {
            workRepository.delete(work)
        }
    }

    fun getWorkServiceById(workId: String): WorkService? {
        return workDataRepository.allWorks.find { it.id == workId }
    }

    fun clearCurrentProject(onFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val roomsList = rooms.value
                roomsList.forEach { room ->
                    workRepository.deleteWorksByRoomId(room.id)
                }
                roomsList.forEach { room ->
                    roomRepository.delete(room)
                }
                projectStateRepository.setGlobalDiscount(0.0)
                projectStateRepository.setSelectedRoomId(null)
                launch(Dispatchers.Main) {
                    onFinished()
                }
            } catch (e: Exception) {
                L.e("DoneScreenViewModel: Error clearing project", e)
            }
        }
    }
}
