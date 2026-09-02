package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.WorkCategory
import com.void_dev_ua.renovum.domain.model.WorkSection
import com.void_dev_ua.renovum.domain.model.WorkService
import com.void_dev_ua.renovum.domain.model.WorkUnit
import com.void_dev_ua.renovum.core.util.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    private val workRepository: WorkRepository,
    private val workDataRepository: WorkDataRepository
) : ViewModel() {

    val appliedWorks: StateFlow<List<AppliedWork>> = workRepository.allWorks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var projectDiscountPercent by mutableDoubleStateOf(0.0)
        private set

    var showDiscountDialog by mutableStateOf(false)
    var workToEdit by mutableStateOf<AppliedWork?>(null)
    var lastSelectedCategory by mutableStateOf<WorkCategory?>(null)

    val totalRawSumState: StateFlow<Double> = appliedWorks
        .map { worksList -> worksList.sumOf { it.priceAtTime * it.quantity } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )

    val totalDiscountedSumState: StateFlow<Double> = combine(totalRawSumState, snapshotFlow { projectDiscountPercent }) { rawSum, discount ->
        rawSum * (1.0 - discount / 100.0)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0.0
    )

    val allWorksFlow = workDataRepository.allWorksFlow

    init {
        viewModelScope.launch {
            workDataRepository.loadWorks()
        }
    }

    fun getAllSections() = workDataRepository.allSections
    fun getCategoriesForSection(section: WorkSection) = workDataRepository.getCategoriesForSection(section)
    fun getWorksForCategory(category: WorkCategory) = workDataRepository.getWorksForCategory(category)

    fun getWorkServiceById(workId: String): WorkService? {
        return workDataRepository.allWorks.find { it.id == workId }
    }

    fun getGroupedWorksState(rooms: List<RoomEntity>): StateFlow<Map<RoomEntity, List<Pair<AppliedWork, WorkService>>>> {
        return combine(appliedWorks, workDataRepository.allWorksFlow) { worksList, allServices ->
            worksList.groupBy { work ->
                rooms.find { it.id == work.roomId }
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
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )
    }

    fun getWorksWithStatusState(currentRoomId: String): StateFlow<Map<String, Boolean>> {
        return appliedWorks
            .map { applied ->
                applied.filter { it.roomId == currentRoomId }
                    .associate { it.workId to true }
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyMap())
    }

    fun updateDiscount(newPercent: Double) {
        projectDiscountPercent = newPercent.coerceIn(0.0, 100.0)
        L.d("WorkViewModel: Discount updated to $projectDiscountPercent%")
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
            L.d("WorkViewModel: Saved ${work.name} to DB for ${room.name}")
        }
    }

    fun deleteAppliedWork(work: AppliedWork) {
        viewModelScope.launch {
            workRepository.delete(work)
            L.d("WorkViewModel: Removed from DB: ${work.workId}")
        }
    }

    fun updateAppliedWork(originalWork: AppliedWork, newPrice: Double, newQuantity: Double) {
        viewModelScope.launch {
            val updatedWork = originalWork.copy(priceAtTime = newPrice, quantity = newQuantity)
            workRepository.update(updatedWork)
            L.d("WorkViewModel: Updated in DB. New total: ${newPrice * newQuantity}")
        }
    }

    fun deleteWorksByRoomId(roomId: String) {
        viewModelScope.launch {
            workRepository.deleteWorksByRoomId(roomId)
        }
    }
}
