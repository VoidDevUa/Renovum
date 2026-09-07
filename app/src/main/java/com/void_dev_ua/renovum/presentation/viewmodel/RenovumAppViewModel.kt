package com.void_dev_ua.renovum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.void_dev_ua.renovum.domain.model.RoomEntity
import com.void_dev_ua.renovum.domain.model.AppliedWork
import com.void_dev_ua.renovum.data.repositories.RoomRepository
import com.void_dev_ua.renovum.data.repositories.WorkRepository
import com.void_dev_ua.renovum.data.repositories.WorkDataRepository
import com.void_dev_ua.renovum.domain.repository.ProjectStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RenovumAppViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val workRepository: WorkRepository,
    private val projectStateRepository: ProjectStateRepository,
    private val workDataRepository: WorkDataRepository
) : ViewModel() {

    val rooms: StateFlow<List<RoomEntity>> = roomRepository.allRooms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedRoomId = projectStateRepository.selectedRoomId

    val selectedRoom: StateFlow<RoomEntity?> = combine(rooms, selectedRoomId) { roomList, id ->
        roomList.find { it.id == id } ?: roomList.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val totalRawSum: StateFlow<Double> = workRepository.allWorks
        .map { worksList -> worksList.sumOf { it.priceAtTime * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val totalDiscountedSum: StateFlow<Double> = combine(totalRawSum, projectStateRepository.globalDiscount) { rawSum, discount ->
        rawSum * (1.0 - discount / 100.0)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun selectRoom(room: RoomEntity) {
        projectStateRepository.setSelectedRoomId(room.id)
    }

    fun showDiscountDialog() {
        // Since we need to show dialog from global top bar, we can use the repository to trigger it
        // but for now let's keep it simple
    }

    init {
        viewModelScope.launch {
            workDataRepository.loadWorks()
        }
        // Sync the repo with the first room if nothing is selected
        viewModelScope.launch {
            rooms.collect { list ->
                if (projectStateRepository.selectedRoomId.value == null && list.isNotEmpty()) {
                    projectStateRepository.setSelectedRoomId(list.first().id)
                }
            }
        }
    }
}
